package hexlet.trainee.springtrainee.JpaSpecifications.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JPASPostParamsDTO {
    private String nameContains;
    private Long authorId;
    private LocalDate createdAtGreaterThan;
    private LocalDate createdAtLessThan;
}
