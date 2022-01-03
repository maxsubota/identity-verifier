package subota.max.verification.api.dto;

import javax.validation.constraints.NotEmpty;

public record Subject(
    @NotEmpty(message = "Identity cannot be null or empty")
    String identity,
    @NotEmpty(message = "type cannot be null or empty")
    String type
) {
}
