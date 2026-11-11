package hexlet.trainee.springtrainee.validation.dto;

import hexlet.trainee.springtrainee.validation.model.ValidationUser;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ValidationUserMapper {
    ValidationUser map(ValidationUserCreateDTO dto);
    ValidationUserDTO map(ValidationUser map);
}
