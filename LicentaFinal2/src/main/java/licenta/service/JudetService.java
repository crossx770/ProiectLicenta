package licenta.service;

import licenta.service.dto.JudetDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link licenta.domain.Judet}.
 */
public interface JudetService {
    /**
     * Save a judet.
     *
     * @param judetDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<JudetDTO> save(JudetDTO judetDTO);

    /**
     * Partially updates a judet.
     *
     * @param judetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<JudetDTO> partialUpdate(JudetDTO judetDTO);

    /**
     * Get all the judets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<JudetDTO> findAll(Pageable pageable);

    /**
     * Returns the number of judets available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" judet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<JudetDTO> findOne(Long id);

    /**
     * Delete the "id" judet.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
