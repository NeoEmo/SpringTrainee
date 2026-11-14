package hexlet.trainee.springtrainee.JpaSpecifications.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JPASPostDTO {
    private Long id;
    private Long authorId;
    private String slug;
    private String name;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
