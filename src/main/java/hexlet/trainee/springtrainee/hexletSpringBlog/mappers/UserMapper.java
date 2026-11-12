package hexlet.trainee.springtrainee.hexletSpringBlog.mappers;


import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserCreateDTO dto);

    @Mapping(target = "password", source = "newPassword")
    void updateEntity(UserPatchDTO dto, @MappingTarget User user);
}
