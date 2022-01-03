package subota.max.template.api.dto;

import javax.validation.constraints.NotNull;

public record Variables(
    @NotNull
    String code
) {
}
