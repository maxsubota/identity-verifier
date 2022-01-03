package subota.max.verification.infrastructure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "verification.settings")
public record VerificationSettingsProperty(
    int codeLength,
    int ttlInSeconds,
    int attemptsLimit
) {
}
