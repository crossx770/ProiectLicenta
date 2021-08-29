package licenta.repository;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import licenta.domain.Post;
import licenta.repository.rowmapper.CategoryRowMapper;
import licenta.repository.rowmapper.CityRowMapper;
import licenta.repository.rowmapper.JudetRowMapper;
import licenta.repository.rowmapper.PostRowMapper;
import licenta.repository.rowmapper.SubCategoryRowMapper;
import licenta.repository.rowmapper.UserRowMapper;
import licenta.service.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Spring Data SQL reactive custom repository implementation for the Post entity.
 */
@SuppressWarnings("unused")
class PostRepositoryInternalImpl implements PostRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final PostRowMapper postMapper;

    private static final Table entityTable = Table.aliased("post", EntityManager.ENTITY_ALIAS);
    private static final Table user_postTable = Table.aliased("jhi_user", "user_post");

    public PostRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        PostRowMapper postMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public Flux<Post> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Post> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Post> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = PostSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(user_postTable, "user_post"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(user_postTable)
            .on(Column.create("user_post_id", entityTable))
            .equals(Column.create("id", user_postTable));

        String select = entityManager.createSelect(selectFrom, Post.class, pageable, criteria);
        String alias = entityTable.getReferenceName().getReference();
        String selectWhere = Optional
            .ofNullable(criteria)
            .map(
                crit ->
                    new StringBuilder(select)
                        .append(" ")
                        .append("WHERE")
                        .append(" ")
                        .append(alias)
                        .append(".")
                        .append(crit.toString())
                        .toString()
            )
            .orElse(select); // TODO remove once https://github.com/spring-projects/spring-data-jdbc/issues/907 will be fixed
        return db.sql(selectWhere).map(this::process);
    }

    @Override
    public Flux<Post> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Post> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Post process(Row row, RowMetadata metadata) {
        Post entity = postMapper.apply(row, "e");
        entity.setUser_post(userMapper.apply(row, "user_post"));
        return entity;
    }

    @Override
    public <S extends Post> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Post> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Post with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Post entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class PostSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("is_promoted", table, columnPrefix + "_is_promoted"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));
        columns.add(Column.aliased("price", table, columnPrefix + "_price"));
        columns.add(Column.aliased("user_post_id", table, columnPrefix + "_user_post_id"));
        columns.add(Column.aliased("judet", table, columnPrefix + "_judet"));
        columns.add(Column.aliased("city", table, columnPrefix + "_city"));
        columns.add(Column.aliased("category", table, columnPrefix + "_category"));
        columns.add(Column.aliased("subcategory", table, columnPrefix + "_subcategory"));
        return columns;
    }
}
