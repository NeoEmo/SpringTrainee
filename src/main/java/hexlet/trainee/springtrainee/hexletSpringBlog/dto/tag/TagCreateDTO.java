package hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateDTO {
    private String name;

    private Long postId;
}
