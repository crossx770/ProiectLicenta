package licenta.repository;

import licenta.domain.Judet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Judet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JudetRepository extends R2dbcRepository<Judet, Long>, JudetRepositoryInternal {
    Flux<Judet> findAllBy(Pageable pageable);

    // just to avoid having unambigous methods
    @Override
    Flux<Judet> findAll();

    @Override
    Mono<Judet> findById(Long id);

    @Override
    <S extends Judet> Mono<S> save(S entity);
}

interface JudetRepositoryInternal {
    <S extends Judet> Mono<S> insert(S entity);
    <S extends Judet> Mono<S> save(S entity);
    Mono<Integer> update(Judet entity);

    Flux<Judet> findAll();
    Mono<Judet> findById(Long id);
    Flux<Judet> findAllBy(Pageable pageable);
    Flux<Judet> findAllBy(Pageable pageable, Criteria criteria);
}
