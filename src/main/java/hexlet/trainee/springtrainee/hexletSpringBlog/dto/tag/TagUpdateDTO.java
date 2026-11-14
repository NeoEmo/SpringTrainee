package hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TagUpdateDTO {
    private JsonNullable<String> name;
    private JsonNullable<Long> postId;
}
