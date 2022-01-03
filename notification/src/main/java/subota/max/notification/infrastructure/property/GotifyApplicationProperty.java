package subota.max.notification.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "notification.gotify.application")
public record GotifyApplicationProperty(

    String username,

    String password,

    String name,

    String description,

    String token
) {
}
