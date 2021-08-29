package licenta.service.mapper;

import licenta.domain.*;
import licenta.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { UserMapper.class }
)
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "user_post", source = "user_post", qualifiedByName = "login")
    PostDTO toDto(Post s);
}
