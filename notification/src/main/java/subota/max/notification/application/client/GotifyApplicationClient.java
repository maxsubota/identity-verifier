package subota.max.notification.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import subota.max.notification.application.client.dto.gotify.CreateApplicationRequest;
import subota.max.notification.application.client.dto.gotify.CreateApplicationResponse;
import subota.max.notification.infrastructure.config.FeignClientConfig;

@FeignClient(name = "gotifyClient", url = "${notification.gotify.url}", configuration = FeignClientConfig.class)
public interface GotifyApplicationClient {

    @PostMapping("/application")
    CreateApplicationResponse createApplication(@RequestBody CreateApplicationRequest createApplicationRequest);
}
