package hexlet.trainee.springtrainee.hexletSpringBlog.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long id;

    private String slug;

    private String content;

    @NotNull
    private Long userId;

    private String authorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private List<TagDTO> tags;
}
