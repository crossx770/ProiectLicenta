package licenta.repository;

import licenta.domain.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the SubCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoryRepository extends R2dbcRepository<SubCategory, Long>, SubCategoryRepositoryInternal {
    Flux<SubCategory> findAllBy(Pageable pageable);

    @Query("SELECT * FROM sub_category entity WHERE entity.category_id = :id")
    Flux<SubCategory> findByCategory(Long id);

    @Query("SELECT * FROM sub_category entity WHERE entity.category_id IS NULL")
    Flux<SubCategory> findAllWhereCategoryIsNull();

    // just to avoid having unambigous methods
    @Override
    Flux<SubCategory> findAll();

    @Override
    Mono<SubCategory> findById(Long id);

    @Override
    <S extends SubCategory> Mono<S> save(S entity);
}

interface SubCategoryRepositoryInternal {
    <S extends SubCategory> Mono<S> insert(S entity);
    <S extends SubCategory> Mono<S> save(S entity);
    Mono<Integer> update(SubCategory entity);

    Flux<SubCategory> findAll();
    Mono<SubCategory> findById(Long id);
    Flux<SubCategory> findAllBy(Pageable pageable);
    Flux<SubCategory> findAllBy(Pageable pageable, Criteria criteria);
}
