package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostParamsDTO {
    private String nameContains;
    private Long authorId;
    private LocalDate createdAtGreaterThan;
    private LocalDate createdAtLessThan;
    private String tagsIn;
    private Long tagId;
}
