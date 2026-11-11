package hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOPost;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DTOPostMapper {
    public abstract DTOPost map (DTOPostCreateDTO data);
    public abstract DTOPostDTO map (DTOPost model);
    public abstract void update(DTOPostUpdateDTO data, @MappingTarget DTOPost model);
}
