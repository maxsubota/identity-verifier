package subota.max.notification.domain.service;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.notification.application.MobileNotificationSender;
import subota.max.notification.domain.Notification;
import subota.max.notification.infrastructure.property.MobileProperty;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NotificationService(channel = ConfirmationType.MOBILE_CONFIRMATION)
public class MobileNotificationService implements NotificationSenderService {
    MobileNotificationSender mobileNotificationSender;
    MobileProperty mobileProperty;

    @Override
    public boolean sendNotification(Notification notification) {
        try {
            mobileNotificationSender.sendMessage(mobileProperty.title(), notification.getBody());

        } catch (FeignException e) {
            log.error("Failed to dispatch mobile notification", e);
            return false;
        }
        return true;
    }
}
