package subota.max.notification.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "notification.email")
public record EmailProperty(
    String from
) {
}
