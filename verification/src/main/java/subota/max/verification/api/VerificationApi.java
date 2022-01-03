package subota.max.verification.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import subota.max.verification.api.dto.CreateVerificationRequest;
import subota.max.verification.api.dto.CreateVerificationResponse;
import subota.max.verification.api.dto.VerificationConfirmationRequest;

import javax.validation.Valid;

@RequestMapping("/verifications")
public interface VerificationApi {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    CreateVerificationResponse createVerification(@Valid @RequestBody CreateVerificationRequest verification);

    @PutMapping("/{verificationId}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> confirmVerification(
        @PathVariable("verificationId") String verificationId,
        @Valid @RequestBody VerificationConfirmationRequest confirmation
    );
}
