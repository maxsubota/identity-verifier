package subota.max.verification.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import subota.max.verification.api.VerificationApi;
import subota.max.verification.api.dto.CreateVerificationRequest;
import subota.max.verification.api.dto.CreateVerificationResponse;
import subota.max.verification.api.dto.VerificationConfirmationRequest;
import subota.max.verification.domain.service.DefaultVerificationService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationController implements VerificationApi {
    DefaultVerificationService verificationService;

    @Override
    public CreateVerificationResponse createVerification(CreateVerificationRequest verification) {
        return verificationService.createVerification(verification);
    }

    @Override
    public ResponseEntity<Void> confirmVerification(String verificationId, VerificationConfirmationRequest confirmation) {
        verificationService.confirmVerification(verificationId, confirmation);

        return ResponseEntity.noContent().build();
    }
}
