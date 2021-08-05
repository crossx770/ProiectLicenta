package licenta.service.impl;

import licenta.domain.Judet;
import licenta.repository.JudetRepository;
import licenta.service.JudetService;
import licenta.service.dto.JudetDTO;
import licenta.service.mapper.JudetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Judet}.
 */
@Service
@Transactional
public class JudetServiceImpl implements JudetService {

    private final Logger log = LoggerFactory.getLogger(JudetServiceImpl.class);

    private final JudetRepository judetRepository;

    private final JudetMapper judetMapper;

    public JudetServiceImpl(JudetRepository judetRepository, JudetMapper judetMapper) {
        this.judetRepository = judetRepository;
        this.judetMapper = judetMapper;
    }

    @Override
    public Mono<JudetDTO> save(JudetDTO judetDTO) {
        log.debug("Request to save Judet : {}", judetDTO);
        return judetRepository.save(judetMapper.toEntity(judetDTO)).map(judetMapper::toDto);
    }

    @Override
    public Mono<JudetDTO> partialUpdate(JudetDTO judetDTO) {
        log.debug("Request to partially update Judet : {}", judetDTO);

        return judetRepository
            .findById(judetDTO.getId())
            .map(
                existingJudet -> {
                    judetMapper.partialUpdate(existingJudet, judetDTO);

                    return existingJudet;
                }
            )
            .flatMap(judetRepository::save)
            .map(judetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<JudetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Judets");
        return judetRepository.findAllBy(pageable).map(judetMapper::toDto);
    }

    @Override
    public Flux<JudetDTO> findAllWithoutPagination() {
        return judetRepository.findAll().map(judetMapper::toDto);
    }

    public Mono<Long> countAll() {
        return judetRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<JudetDTO> findOne(Long id) {
        log.debug("Request to get Judet : {}", id);
        return judetRepository.findById(id).map(judetMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Judet : {}", id);
        return judetRepository.deleteById(id);
    }
}
