package subota.max.notification.application.client.dto.gotify;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMessageResponse {
    Long id;
    Long appId;
    String message;
    String title;
    Integer priority;
    Date date;
}
