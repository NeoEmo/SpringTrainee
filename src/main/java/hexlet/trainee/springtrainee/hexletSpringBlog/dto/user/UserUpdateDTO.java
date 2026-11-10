package hexlet.trainee.springtrainee.hexletSpringBlog.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    @Size(min = 10, max = 100)
    private String newPassword;
}
