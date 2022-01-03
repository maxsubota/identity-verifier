package subota.max.notification.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "notification.mobile")
public record MobileProperty(
    String title
) {
}
