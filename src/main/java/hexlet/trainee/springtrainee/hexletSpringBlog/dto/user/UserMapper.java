package hexlet.trainee.springtrainee.hexletSpringBlog.dto.user;


import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserCreateDTO dto);
    void updateEntity(UserUpdateDTO dto, @MappingTarget User user);
}
