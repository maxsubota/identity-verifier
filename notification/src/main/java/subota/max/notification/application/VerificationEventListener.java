package subota.max.notification.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import subota.max.core.event.DomainEvent;
import subota.max.notification.domain.service.VerificationCreatedEventHandler;

import static subota.max.core.event.DomainEventType.VERIFICATION_CREATED;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationEventListener {

    VerificationCreatedEventHandler verificationCreatedEventHandler;

    @RabbitListener(queues = "${notification.queues.verification}")
    public void listen(@Payload DomainEvent event) {
        if (event.getType() == VERIFICATION_CREATED) {
            verificationCreatedEventHandler.handleEvent(event);
        }
    }
}
