package subota.max.template.domain.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.template.domain.exception.TemplateNotFoundException;
import subota.max.template.domain.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateService {
    TemplateRepository templateRepository;

    public String getTemplateName(ConfirmationType slug) {
        return templateRepository.findBySlug(slug)
            .orElseThrow(() -> new TemplateNotFoundException("Template not found"))
            .getTemplateName();
    }
}
