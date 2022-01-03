package subota.max.notification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.core.event.DomainEvent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Version;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.TemporalType.TIMESTAMP;
import static subota.max.core.event.DomainEventType.NOTIFICATION_CREATED;
import static subota.max.core.event.DomainEventType.NOTIFICATION_DISPATCHED;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends AbstractAggregateRoot<Notification> {

    @Id
    String id;

    String recipient;

    @Enumerated(EnumType.STRING)
    ConfirmationType channel;

    @Column(columnDefinition = "text")
    String body;

    boolean dispatched;

    int retryCount;

    @CreatedDate
    @Temporal(TIMESTAMP)
    Date createdAt;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    Date updatedAt;

    @Version
    Long version;

    public void markAsDispatched() {
        dispatched = true;
        Map<String, String> payload = Map.of(
            "recipient", recipient,
            "channel", channel.toString()
        );
        registerEvent(new DomainEvent(NOTIFICATION_DISPATCHED, id, payload));
    }

    public void decrementRetryCount() {
        retryCount--;
    }

    public Notification(String recipient, ConfirmationType channel, String body, int retryCount) {
        this.id = UUID.randomUUID().toString();
        this.recipient = recipient;
        this.channel = channel;
        this.body = body;
        this.retryCount = retryCount;
        registerEvent(new DomainEvent(NOTIFICATION_CREATED, id, this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
