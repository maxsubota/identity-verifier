package subota.max.notification.application.client.dto.gotify;

public record CreateMessageRequest(
    String title,
    String message,
    Integer priority
) {
}
