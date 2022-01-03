package subota.max.template.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import subota.max.core.confirmation.ConfirmationType;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Template {

    ConfirmationType slug;

    String templateName;
}
