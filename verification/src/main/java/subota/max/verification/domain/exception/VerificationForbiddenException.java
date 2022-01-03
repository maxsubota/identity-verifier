package subota.max.verification.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class VerificationForbiddenException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = 5916741399416388831L;

    public VerificationForbiddenException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}
