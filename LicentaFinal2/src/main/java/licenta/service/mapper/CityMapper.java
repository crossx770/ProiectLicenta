package licenta.service.mapper;

import licenta.domain.*;
import licenta.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = { JudetMapper.class })
public interface CityMapper extends EntityMapper<CityDTO, City> {
    CityDTO toDto(City s);
}
