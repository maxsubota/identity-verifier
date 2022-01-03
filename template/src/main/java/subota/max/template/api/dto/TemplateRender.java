package subota.max.template.api.dto;

import subota.max.core.confirmation.ConfirmationType;

public record TemplateRender(
    ConfirmationType slug,
    Variables variables
) {
}
