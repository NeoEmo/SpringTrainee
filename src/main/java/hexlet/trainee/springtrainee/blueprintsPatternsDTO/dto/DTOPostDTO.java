package hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DTOPostDTO {
    private Long id;
    private String slug;
    private String name;
    private String body;
    private LocalDateTime createdAt;
}
