package subota.max.verification.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class VerificationExpiredException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = 4808305255950928393L;

    public VerificationExpiredException(String reason) {
        super(HttpStatus.GONE, reason);
    }
}
