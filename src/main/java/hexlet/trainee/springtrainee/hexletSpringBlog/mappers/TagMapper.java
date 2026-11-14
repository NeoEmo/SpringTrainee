package hexlet.trainee.springtrainee.hexletSpringBlog.mappers;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagUpdateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Tag;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {
    @Mapping(target = "postId", source = "post.id")
    TagDTO toDTO(Tag tag);

    @Mapping(target = "post", ignore = true)
    Tag toEntity(TagCreateDTO dto);

    @Mapping(target = "post", ignore = true)
    void update(TagUpdateDTO dto, @MappingTarget Tag tag);
}
