package hexlet.trainee.springtrainee.JpaSpecifications.mapper;

import hexlet.trainee.springtrainee.JpaSpecifications.DTO.JPASPostDTO;
import hexlet.trainee.springtrainee.JpaSpecifications.model.JPASPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JPASPostMapper {
    @Mapping(source = "author.id", target = "authorId")
    JPASPostDTO toDTO(JPASPost post);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", ignore = true)
    JPASPost toEntity(JPASPostDTO dto);
}
