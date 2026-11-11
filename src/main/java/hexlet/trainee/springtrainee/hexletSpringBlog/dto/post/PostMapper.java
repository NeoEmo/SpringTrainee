package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    PostDTO toDTO(Post post);

    Post toEntity(PostCreateDTO dto);

    void updateEntityFromDTO(PostUpdateDTO dto, @MappingTarget Post post);
}
