package licenta.service.mapper;

import licenta.domain.*;
import licenta.service.dto.JudetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Judet} and its DTO {@link JudetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JudetMapper extends EntityMapper<JudetDTO, Judet> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JudetDTO toDtoId(Judet judet);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    JudetDTO toDtoName(Judet judet);
}
