package subota.max.verification.api.dto;

import subota.max.verification.application.SubjectValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record CreateVerificationRequest(
    @NotNull(message = "Subject cannot be null")
    @SubjectValidation
    @Valid
    Subject subject
) {
}
