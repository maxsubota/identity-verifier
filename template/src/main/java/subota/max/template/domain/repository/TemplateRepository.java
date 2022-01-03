package subota.max.template.domain.repository;

import org.springframework.stereotype.Repository;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.template.domain.Template;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static subota.max.core.confirmation.ConfirmationType.EMAIL_CONFIRMATION;
import static subota.max.core.confirmation.ConfirmationType.MOBILE_CONFIRMATION;

@Repository
public class TemplateRepository {

    private final Map<ConfirmationType, Template> map = new EnumMap<>(ConfirmationType.class);

    TemplateRepository() {
        map.put(MOBILE_CONFIRMATION, new Template(MOBILE_CONFIRMATION, "sms-verification.txt"));
        map.put(EMAIL_CONFIRMATION, new Template(EMAIL_CONFIRMATION, "email-verification.html"));
    }

    public Optional<Template> findBySlug(ConfirmationType slug) {
        return Optional.ofNullable(map.get(slug));
    }
}
