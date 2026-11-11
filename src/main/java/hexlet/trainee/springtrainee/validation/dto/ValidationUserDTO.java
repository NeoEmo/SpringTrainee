package hexlet.trainee.springtrainee.validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationUserDTO {
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotNull
    @Size(min = 8)
    private String password;
}
