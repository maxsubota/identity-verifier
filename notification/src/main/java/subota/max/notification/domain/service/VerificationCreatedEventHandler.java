package subota.max.notification.domain.service;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import subota.max.core.event.DomainEvent;
import subota.max.core.event.DomainEventHandler;
import subota.max.notification.application.client.TemplateClient;
import subota.max.notification.application.client.dto.template.TemplateRender;
import subota.max.notification.application.client.dto.template.Variables;
import subota.max.notification.application.client.dto.verification.VerificationDto;
import subota.max.notification.domain.Notification;
import subota.max.notification.domain.repository.NotificationRepository;
import subota.max.notification.infrastructure.property.RetryProperty;

import static subota.max.core.event.DomainEventType.VERIFICATION_CREATED;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationCreatedEventHandler implements DomainEventHandler<DomainEvent> {
    Gson gson = new Gson();
    TemplateClient templateClient;
    RetryProperty retryProperty;
    NotificationSenderServiceFactory notificationSenderServiceFactory;
    NotificationRepository notificationRepository;

    @Override
    @Transactional(noRollbackFor = AmqpRejectAndDontRequeueException.class)
    public void handleEvent(DomainEvent event) {
        if (event.getType() != VERIFICATION_CREATED) {
            return;
        }

        VerificationDto verification = gson.fromJson(event.getPayload(), VerificationDto.class);

        Variables variables = new Variables(verification.getCode());
        TemplateRender template = new TemplateRender(verification.getIdentityType(), variables);
        String body = templateClient.getTemplate(template);

        Notification notification = new Notification(
            verification.getIdentity(),
            verification.getIdentityType(),
            body,
            retryProperty.attempts()
        );
        notificationRepository.save(notification);

        NotificationSenderService notificationSender =
            notificationSenderServiceFactory.getNotificationSenderService(notification.getChannel());

        if (notificationSender.sendNotification(notification)) {
            notification.markAsDispatched();
            notificationRepository.save(notification);
        } else {
            log.error("Failed to dispatch notification {}", notification.getId());
            throw new AmqpRejectAndDontRequeueException("This notification will be send again later.");
        }
    }
}
