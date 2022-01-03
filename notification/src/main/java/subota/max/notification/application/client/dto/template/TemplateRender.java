package subota.max.notification.application.client.dto.template;

import subota.max.core.confirmation.ConfirmationType;

public record TemplateRender(
    ConfirmationType slug,
    Variables variables
) {
}
