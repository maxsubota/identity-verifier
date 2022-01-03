package subota.max.notification.domain.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subota.max.notification.domain.Notification;
import subota.max.notification.domain.repository.NotificationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RetryNotificationService {
    NotificationRepository notificationRepository;
    NotificationSenderServiceFactory notificationSenderServiceFactory;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void retry() {
        List<Notification> notifications = notificationRepository.findAllByDispatchedFalseAndRetryCountGreaterThan(0);
        if (!notifications.isEmpty()) {
            log.info("Found {} not dispatched notification", notifications.size());
            log.info("Trying to retry");
        }

        for (Notification notification : notifications) {
            NotificationSenderService notificationSender =
                notificationSenderServiceFactory.getNotificationSenderService(notification.getChannel());

            if (notificationSender.sendNotification(notification)) {
                notification.markAsDispatched();
            } else {
                log.error("Failed to dispatch notification {}", notification.getId());
            }
            notification.decrementRetryCount();
            notificationRepository.save(notification);
        }
    }
}
