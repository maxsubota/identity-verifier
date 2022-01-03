package subota.max.verification.domain.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import subota.max.core.event.DomainEvent;
import subota.max.outbox.event.service.OutboxPublishedEventHandler;
import subota.max.verification.infrastructure.property.QueuesProperty;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationDomainEventListener {

    OutboxPublishedEventHandler outboxPublishedEventHandler;
    RabbitTemplate rabbitTemplate;
    QueuesProperty queuesProperty;

    @TransactionalEventListener
    public void onVerificationEvent(DomainEvent verificationEvent) {
        rabbitTemplate.convertAndSend(queuesProperty.verification(), verificationEvent);
        outboxPublishedEventHandler.handleEvent(verificationEvent);
    }

}
