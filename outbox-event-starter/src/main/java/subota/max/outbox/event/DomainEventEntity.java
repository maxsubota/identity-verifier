package subota.max.outbox.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import subota.max.core.entity.AuditableEntity;
import subota.max.core.event.DomainEvent;
import subota.max.core.event.DomainEventType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "domain_event")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainEventEntity extends AuditableEntity {

    @Id
    String id;

    @Enumerated(EnumType.STRING)
    DomainEventType type;

    String aggregateId;

    @Column(columnDefinition = "text")
    String payload;

    @Enumerated(EnumType.STRING)
    DomainEventStatus status;

    public static DomainEventEntity of(DomainEvent domainEvent) {
        return new DomainEventEntity(
            domainEvent.getId(),
            domainEvent.getType(),
            domainEvent.getAggregateId(),
            domainEvent.getPayload(),
            DomainEventStatus.CREATED
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DomainEventEntity that = (DomainEventEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
