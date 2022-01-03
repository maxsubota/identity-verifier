package subota.max.notification.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import subota.max.notification.application.client.dto.gotify.CreateMessageRequest;
import subota.max.notification.application.client.dto.gotify.CreateMessageResponse;

@FeignClient(name = "gotifyMessageClient", url = "${notification.gotify.url}")
public interface GotifyMessageClient {

    @PostMapping("/message")
    CreateMessageResponse createMessage(
        @RequestParam("token") String token,
        @RequestBody CreateMessageRequest createMessageRequest
    );
}
