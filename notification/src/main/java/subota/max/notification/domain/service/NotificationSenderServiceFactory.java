package subota.max.notification.domain.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import subota.max.core.confirmation.ConfirmationType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationSenderServiceFactory {

    Map<ConfirmationType, NotificationSenderService> notificationSenderServiceMap;

    public NotificationSenderServiceFactory(List<NotificationSenderService> notificationSenderServices) {
        this.notificationSenderServiceMap = notificationSenderServices.stream()
            .collect(Collectors.toMap(this::getChannel, Function.identity()));
    }

    public NotificationSenderService getNotificationSenderService(ConfirmationType confirmationType) {
        NotificationSenderService notificationSender = notificationSenderServiceMap.get(confirmationType);
        if (notificationSender == null) {
            throw new UnsupportedOperationException("Confirmation type " + confirmationType + " not supported");
        }

        return notificationSender;
    }

    private ConfirmationType getChannel(NotificationSenderService notificationSenderService) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(notificationSenderService.getClass(), NotificationService.class))
            .map(NotificationService::channel)
            .orElseThrow();
    }
}
