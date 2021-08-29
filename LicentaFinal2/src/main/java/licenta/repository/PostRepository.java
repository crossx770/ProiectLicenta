package licenta.repository;

import licenta.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends R2dbcRepository<Post, Long>, PostRepositoryInternal {
    Flux<Post> findAllBy(Pageable pageable);


    @Query("SELECT * FROM post entity WHERE entity.judet = :judet AND entity.city = :city AND entity.category = :category AND entity.subcategory = :subcategory")
    Flux<Post> findAllByCriteria(String judet, String city, String category, String subcategory, Pageable pageable);

    @Query("SELECT * FROM post entity LEFT JOIN jhi_user u ON u.id = entity.user_post_id WHERE u.login = :user")
    Flux<Post> findAllByUser(Pageable pageable, String user);

    @Query("SELECT * FROM post entity WHERE entity.user_post_id = :id")
    Flux<Post> findByUser_post(Long id);

    @Query("SELECT * FROM post entity WHERE entity.user_post_id IS NULL")
    Flux<Post> findAllWhereUser_postIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<Post> findAll();

    @Override
    Mono<Post> findById(Long id);

    @Override
    <S extends Post> Mono<S> save(S entity);
}

interface PostRepositoryInternal {
    <S extends Post> Mono<S> insert(S entity);
    <S extends Post> Mono<S> save(S entity);
    Mono<Integer> update(Post entity);

    Flux<Post> findAll();
    Mono<Post> findById(Long id);
    Flux<Post> findAllBy(Pageable pageable);
    Flux<Post> findAllBy(Pageable pageable, Criteria criteria);
}
