package subota.max.notification.application.client.dto.verification;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import subota.max.core.confirmation.ConfirmationType;

import java.io.Serial;
import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -7420173894358722538L;

    String identity;

    ConfirmationType identityType;

    String code;
}
