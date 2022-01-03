package subota.max.outbox.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import subota.max.core.event.DomainEvent;
import subota.max.core.event.DomainEventHandler;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DomainEventListener {

    DomainEventHandler<DomainEvent> outboxEventHandler;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onVerificationEventBeforeCommit(DomainEvent verificationEvent) {
        outboxEventHandler.handleEvent(verificationEvent);
    }
}
