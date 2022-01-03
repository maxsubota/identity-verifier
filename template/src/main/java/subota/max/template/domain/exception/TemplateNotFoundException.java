package subota.max.template.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class TemplateNotFoundException extends ResponseStatusException {
    @Serial
    private static final long serialVersionUID = -3806019049338598376L;

    public TemplateNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
