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
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the Post entity.
 */
@SuppressWarnings("unused")
class PostRepositoryInternalImpl implements PostRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final JudetRowMapper judetMapper;
    private final CityRowMapper cityMapper;
    private final CategoryRowMapper categoryMapper;
    private final SubCategoryRowMapper subcategoryMapper;
    private final PostRowMapper postMapper;

    private static final Table entityTable = Table.aliased("post", EntityManager.ENTITY_ALIAS);
    private static final Table user_postTable = Table.aliased("jhi_user", "user_post");
    private static final Table judet_postTable = Table.aliased("judet", "judet_post");
    private static final Table city_postTable = Table.aliased("city", "city_post");
    private static final Table category_postTable = Table.aliased("category", "category_post");
    private static final Table subCategory_postTable = Table.aliased("sub_category", "subCategory_post");

    public PostRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        JudetRowMapper judetMapper,
        CityRowMapper cityMapper,
        CategoryRowMapper categoryMapper,
        SubCategoryRowMapper subcategoryMapper,
        PostRowMapper postMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.judetMapper = judetMapper;
        this.cityMapper = cityMapper;
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
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
        columns.addAll(JudetSqlHelper.getColumns(judet_postTable, "judet_post"));
        columns.addAll(CitySqlHelper.getColumns(city_postTable, "city_post"));
        columns.addAll(CategorySqlHelper.getColumns(category_postTable, "category_post"));
        columns.addAll(SubCategorySqlHelper.getColumns(subCategory_postTable, "subCategory_post"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(user_postTable)
            .on(Column.create("user_post_id", entityTable))
            .equals(Column.create("id", user_postTable))
            .leftOuterJoin(judet_postTable)
            .on(Column.create("judet_post_id", entityTable))
            .equals(Column.create("id", judet_postTable))
            .leftOuterJoin(city_postTable)
            .on(Column.create("city_post_id", entityTable))
            .equals(Column.create("id", city_postTable))
            .leftOuterJoin(category_postTable)
            .on(Column.create("category_post_id", entityTable))
            .equals(Column.create("id", category_postTable))
            .leftOuterJoin(subCategory_postTable)
            .on(Column.create("sub_category_post_id", entityTable))
            .equals(Column.create("id", subCategory_postTable));

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
        entity.setJudet_post(judetMapper.apply(row, "judet_post"));
        entity.setCity_post(cityMapper.apply(row, "city_post"));
        entity.setCategory_post(categoryMapper.apply(row, "category_post"));
        entity.setSubCategory_post(subcategoryMapper.apply(row, "subCategory_post"));
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
        columns.add(Column.aliased("judet_post_id", table, columnPrefix + "_judet_post_id"));
        columns.add(Column.aliased("city_post_id", table, columnPrefix + "_city_post_id"));
        columns.add(Column.aliased("category_post_id", table, columnPrefix + "_category_post_id"));
        columns.add(Column.aliased("sub_category_post_id", table, columnPrefix + "_sub_category_post_id"));
        return columns;
    }
}
