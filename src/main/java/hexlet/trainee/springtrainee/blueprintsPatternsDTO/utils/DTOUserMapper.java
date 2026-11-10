package hexlet.trainee.springtrainee.blueprintsPatternsDTO.utils;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOUserDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOUser;
import org.springframework.stereotype.Component;

@Component
public class DTOUserMapper {
    public DTOUserDTO toDTO(DTOUser user) {
        var dto = new DTOUserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
