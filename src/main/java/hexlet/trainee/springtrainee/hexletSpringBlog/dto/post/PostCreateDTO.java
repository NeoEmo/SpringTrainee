package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCreateDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String slug;

    @NotBlank
    @Size(min = 10)
    private  String content;

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private String authorName;

    private List<TagCreateDTO> tags;
}
