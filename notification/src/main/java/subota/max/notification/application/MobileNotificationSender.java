package subota.max.notification.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import subota.max.notification.application.client.GotifyApplicationClient;
import subota.max.notification.application.client.GotifyMessageClient;
import subota.max.notification.application.client.dto.gotify.CreateApplicationRequest;
import subota.max.notification.application.client.dto.gotify.CreateMessageRequest;
import subota.max.notification.infrastructure.property.GotifyApplicationProperty;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MobileNotificationSender {
    final GotifyMessageClient gotifyMessageClient;
    final GotifyApplicationClient gotifyApplicationClient;
    final GotifyApplicationProperty gotifyApplicationProperty;

    String token;

    @PostConstruct
    public void init() {
        if (gotifyApplicationProperty.token() == null) {
            String applicationName = gotifyApplicationProperty.name();
            String applicationDescription = gotifyApplicationProperty.description();
            CreateApplicationRequest request = new CreateApplicationRequest(applicationName, applicationDescription);
            token = gotifyApplicationClient.createApplication(request)
                .getToken();
        } else {
            token = gotifyApplicationProperty.token();
        }
    }

    public void sendMessage(String title, String message) {
        gotifyMessageClient.createMessage(token, new CreateMessageRequest(title, message, 1));
    }
}
