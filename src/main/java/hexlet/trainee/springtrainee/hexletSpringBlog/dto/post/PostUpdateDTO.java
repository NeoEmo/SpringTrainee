package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDTO {

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 10)
    private String content;

    private String author;
}
