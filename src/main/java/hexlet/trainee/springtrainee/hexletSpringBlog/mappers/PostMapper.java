package hexlet.trainee.springtrainee.hexletSpringBlog.mappers;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class, TagMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    @Mapping(target = "userId", source = "author.id")
    PostDTO toDTO(Post post);

    @Mapping(target = "author", ignore = true)
    Post toEntity(PostCreateDTO dto);

    @AfterMapping
    default void linkTags(@MappingTarget Post post) {
        if (post.getTags() != null) {
            post.getTags().forEach(tag -> tag.setPost(post));
        }
    }

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void updateEntityFromDTO(PostPatchDTO dto, @MappingTarget Post post);
}
