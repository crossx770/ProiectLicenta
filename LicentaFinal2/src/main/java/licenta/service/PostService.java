package licenta.service;

import licenta.service.dto.PostDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link licenta.domain.Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<PostDTO> save(PostDTO postDTO);

    /**
     * Partially updates a post.
     *
     * @param postDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<PostDTO> partialUpdate(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<PostDTO> findAll(Pageable pageable);

    /**
     * Returns the number of posts available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<PostDTO> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
