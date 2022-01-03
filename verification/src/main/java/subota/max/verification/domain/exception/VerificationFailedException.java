package subota.max.verification.domain.exception;

import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class VerificationFailedException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = 2549423311089350597L;

    public VerificationFailedException(String reason) {
        super(UNPROCESSABLE_ENTITY, reason);
    }
}
