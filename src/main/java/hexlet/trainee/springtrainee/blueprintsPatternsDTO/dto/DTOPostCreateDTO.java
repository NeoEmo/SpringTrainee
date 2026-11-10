package hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOPostCreateDTO {
    private String slug;
    private String name;
    private String body;
}
