package licenta.service.impl;

import licenta.domain.Post;
import licenta.repository.PostRepository;
import licenta.security.SecurityUtils;
import licenta.service.PostService;
import licenta.service.dto.PostDTO;
import licenta.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Mono<PostDTO> save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        return postRepository.save(postMapper.toEntity(postDTO)).map(postMapper::toDto);
    }

    @Override
    public Mono<PostDTO> partialUpdate(PostDTO postDTO) {
        log.debug("Request to partially update Post : {}", postDTO);


        return postRepository
            .findById(postDTO.getId())
            .map(
                existingPost -> {
                    postMapper.partialUpdate(existingPost, postDTO);

                    return existingPost;
                }
            )
            .flatMap(postRepository::save)
            .map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAllBy(pageable).map(postMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Flux<PostDTO> findAllWithUser(Pageable pageable, String user) {
        log.debug("Request to get all Posts");
        return postRepository.findAllByUser(pageable,user).map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<PostDTO> findAllWithFilter(String judet, String city, String category, String subcategory, Pageable pageable) {
        log.debug("Request to get all Posts with filtering");
        return postRepository.findAllByCriteria(judet,city,category,subcategory,pageable).map(postMapper::toDto);
    }

    public Mono<Long> countAll() {
        return postRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        return postRepository.deleteById(id);
    }
}
