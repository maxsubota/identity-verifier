package subota.max.verification.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class VerificationNotFoundException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = 2700801977282651051L;

    public VerificationNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
