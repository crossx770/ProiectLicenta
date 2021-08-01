package licenta.service.mapper;

import licenta.domain.*;
import licenta.service.dto.SubCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubCategory} and its DTO {@link SubCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class })
public interface SubCategoryMapper extends EntityMapper<SubCategoryDTO, SubCategory> {
    @Mapping(target = "category", source = "category", qualifiedByName = "name")
    SubCategoryDTO toDto(SubCategory s);
}
