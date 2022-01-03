package subota.max.notification.domain.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.notification.domain.Notification;
import subota.max.notification.infrastructure.property.EmailProperty;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NotificationService(channel = ConfirmationType.EMAIL_CONFIRMATION)
public class EmailNotificationService implements NotificationSenderService {
    JavaMailSender mailSender;
    EmailProperty emailProperty;

    @Override
    public boolean sendNotification(Notification notification) {
        try {
            MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));

            message.setFrom(new InternetAddress(emailProperty.from()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(notification.getRecipient()));
            message.setContent(notification.getBody(), "text/html");

            mailSender.send(message);

        } catch (AddressException e) {
            log.error("Invalid from address", e);
            return false;

        } catch (MessagingException e) {
            log.error("Failed to construct message", e);
            return false;

        } catch (MailException e) {
            log.error("Failed to dispatch email notification", e);
            return false;
        }

        return true;
    }
}
