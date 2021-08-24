package licenta.service.mapper;

import licenta.domain.*;
import licenta.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { UserMapper.class, JudetMapper.class, CityMapper.class, CategoryMapper.class, SubCategoryMapper.class }
)
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "user_post", source = "user_post", qualifiedByName = "login")
    @Mapping(target = "judet_post", source = "judet_post", qualifiedByName = "name")
    @Mapping(target = "city_post", source = "city_post", qualifiedByName = "name")
    @Mapping(target = "category_post", source = "category_post", qualifiedByName = "name")
    @Mapping(target = "subCategory_post", source = "subCategory_post", qualifiedByName = "name")
    PostDTO toDto(Post s);
}
