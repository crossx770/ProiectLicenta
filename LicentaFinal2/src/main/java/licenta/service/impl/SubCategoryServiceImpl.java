package licenta.service.impl;

import licenta.domain.SubCategory;
import licenta.repository.SubCategoryRepository;
import licenta.service.SubCategoryService;
import licenta.service.dto.SubCategoryDTO;
import licenta.service.mapper.SubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link SubCategory}.
 */
@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryMapper subCategoryMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryMapper = subCategoryMapper;
    }

    @Override
    public Mono<SubCategoryDTO> save(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to save SubCategory : {}", subCategoryDTO);
        return subCategoryRepository.save(subCategoryMapper.toEntity(subCategoryDTO)).map(subCategoryMapper::toDto);
    }

    @Override
    public Mono<SubCategoryDTO> partialUpdate(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to partially update SubCategory : {}", subCategoryDTO);

        return subCategoryRepository
            .findById(subCategoryDTO.getId())
            .map(
                existingSubCategory -> {
                    subCategoryMapper.partialUpdate(existingSubCategory, subCategoryDTO);

                    return existingSubCategory;
                }
            )
            .flatMap(subCategoryRepository::save)
            .map(subCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubCategories");
        return subCategoryRepository.findAllBy(pageable).map(subCategoryMapper::toDto);
    }

    public Mono<Long> countAll() {
        return subCategoryRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SubCategoryDTO> findOne(Long id) {
        log.debug("Request to get SubCategory : {}", id);
        return subCategoryRepository.findById(id).map(subCategoryMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete SubCategory : {}", id);
        return subCategoryRepository.deleteById(id);
    }
}
