package hexlet.trainee.springtrainee.hexletSpringBlog.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserPatchDTO {

    private JsonNullable<String> username;

    @Email
    private JsonNullable<String> email;

    @NotBlank
    private String password;

    @Size(min = 10, max = 100)
    private JsonNullable<String> newPassword;
}
