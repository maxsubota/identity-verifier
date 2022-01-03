package subota.max.notification.domain.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import subota.max.core.confirmation.ConfirmationType;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Component
@Qualifier
public @interface NotificationService {
    ConfirmationType channel();
}
