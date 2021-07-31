package licenta.service.impl;

import licenta.domain.City;
import licenta.repository.CityRepository;
import licenta.service.CityService;
import licenta.service.dto.CityDTO;
import licenta.service.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public Mono<CityDTO> save(CityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        return cityRepository.save(cityMapper.toEntity(cityDTO)).map(cityMapper::toDto);
    }

    @Override
    public Mono<CityDTO> partialUpdate(CityDTO cityDTO) {
        log.debug("Request to partially update City : {}", cityDTO);

        return cityRepository
            .findById(cityDTO.getId())
            .map(
                existingCity -> {
                    cityMapper.partialUpdate(existingCity, cityDTO);

                    return existingCity;
                }
            )
            .flatMap(cityRepository::save)
            .map(cityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAllBy(pageable).map(cityMapper::toDto);
    }

    public Mono<Long> countAll() {
        return cityRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CityDTO> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id).map(cityMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete City : {}", id);
        return cityRepository.deleteById(id);
    }
}
