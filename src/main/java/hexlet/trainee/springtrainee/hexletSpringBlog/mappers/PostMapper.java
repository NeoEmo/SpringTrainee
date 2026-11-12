package hexlet.trainee.springtrainee.hexletSpringBlog.mappers;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = {JsonNullableMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    PostDTO toDTO(Post post);

    Post toEntity(PostCreateDTO dto);

    void updateEntityFromDTO(PostPatchDTO dto, @MappingTarget Post post);
}
