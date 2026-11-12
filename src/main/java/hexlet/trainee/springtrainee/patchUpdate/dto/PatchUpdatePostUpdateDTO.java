package hexlet.trainee.springtrainee.patchUpdate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PatchUpdatePostUpdateDTO {
    @NotNull
    private JsonNullable<String> name;

    @NotNull
    private JsonNullable<String> body;
}
