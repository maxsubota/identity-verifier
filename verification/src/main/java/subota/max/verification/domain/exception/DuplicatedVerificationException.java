package subota.max.verification.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class DuplicatedVerificationException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = -2886667822288086595L;

    public DuplicatedVerificationException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
