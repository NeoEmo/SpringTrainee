package hexlet.trainee.springtrainee.validation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "validation_users")
public class ValidationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotNull
    @Size(min = 8)
    private String password;

//    Пример создания своей валидации
//    @Pattern(regexp = "[A-Z]{2}\\d{6}")
//    private String code;
}
