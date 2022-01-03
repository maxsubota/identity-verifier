package subota.max.verification.api.dto;

import javax.validation.constraints.NotEmpty;

public record VerificationConfirmationRequest(
    @NotEmpty(message = "Code cannot be null or empty")
    String code
) {
}
