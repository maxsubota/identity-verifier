package subota.max.notification.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import subota.max.notification.application.client.dto.template.TemplateRender;

@FeignClient(name = "templateClient", url = "${notification.template.url}")
public interface TemplateClient {

    @PostMapping("/templates/render")
    String getTemplate(TemplateRender templateRender);
}
