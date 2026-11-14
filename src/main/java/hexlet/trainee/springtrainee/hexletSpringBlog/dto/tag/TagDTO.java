package hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Long id;
    private String name;

    @NotNull
    private Long postId;
}
