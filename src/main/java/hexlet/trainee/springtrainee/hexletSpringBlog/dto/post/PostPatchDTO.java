package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class PostPatchDTO {
    @Size(min = 3, max = 100)
    private JsonNullable<String> slug;

    @Size(min = 10)
    private JsonNullable<String> content;

    private JsonNullable<Long> userId;

    private JsonNullable<String> authorName;

    private JsonNullable<List<TagCreateDTO>> tags;
}
