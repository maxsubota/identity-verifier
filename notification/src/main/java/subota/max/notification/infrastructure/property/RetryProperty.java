package subota.max.notification.infrastructure.property;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "notification.retry")
public record RetryProperty(
    int attempts
) {
}
