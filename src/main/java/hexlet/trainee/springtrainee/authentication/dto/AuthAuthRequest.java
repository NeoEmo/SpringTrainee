package hexlet.trainee.springtrainee.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthAuthRequest {
    private String username;
    private String password;
}
