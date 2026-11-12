package hexlet.trainee.springtrainee.patchUpdate.dto;

import hexlet.trainee.springtrainee.patchUpdate.model.PatchUpdatePost;
import org.mapstruct.*;

@Mapper(
        uses = {PatchUpdateJsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PatchUpdatePostMapper {
    public abstract PatchUpdatePostDTO map(PatchUpdatePost post);
    public abstract void update(PatchUpdatePostUpdateDTO dto, @MappingTarget PatchUpdatePost model);
}
