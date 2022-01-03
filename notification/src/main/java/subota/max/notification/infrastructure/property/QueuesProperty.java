package subota.max.notification.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "notification.queues")
public record QueuesProperty(
    String verification
) {
}
