package subota.max.outbox.event.exception;

import java.io.Serial;

public class DomainEventNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3287632311117938590L;

    public DomainEventNotFoundException(String reason) {
        super(reason);
    }
}
