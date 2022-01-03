package subota.max.template.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import subota.max.template.api.TemplateApi;
import subota.max.template.api.dto.TemplateRender;
import subota.max.template.api.dto.Variables;
import subota.max.template.domain.service.TemplateService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateController implements TemplateApi {
    TemplateService templateService;

    @Override
    public String render(TemplateRender templateRender, Model model) {
        Variables variables = templateRender.variables();
        model.addAttribute("code", variables.code());

        return templateService.getTemplateName(templateRender.slug());
    }
}
