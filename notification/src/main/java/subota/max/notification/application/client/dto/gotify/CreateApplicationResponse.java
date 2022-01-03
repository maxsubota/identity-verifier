package subota.max.notification.application.client.dto.gotify;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateApplicationResponse {
    Long id;
    String name;
    String description;
    String image;
    Boolean internal;
    String token;
}
