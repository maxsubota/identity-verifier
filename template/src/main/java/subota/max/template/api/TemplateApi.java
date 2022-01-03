package subota.max.template.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import subota.max.template.api.dto.TemplateRender;

@RequestMapping("/templates")
public interface TemplateApi {

    @PostMapping("/render")
    String render(@RequestBody TemplateRender templateRender, Model model);
}
