package subota.max.notification.domain.service;

import subota.max.notification.domain.Notification;

public interface NotificationSenderService {

    boolean sendNotification(Notification notification);
}
