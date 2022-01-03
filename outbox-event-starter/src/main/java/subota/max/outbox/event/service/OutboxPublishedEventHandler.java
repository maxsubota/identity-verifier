package subota.max.outbox.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import subota.max.core.event.DomainEvent;
import subota.max.core.event.DomainEventHandler;
import subota.max.outbox.event.DomainEventEntity;
import subota.max.outbox.event.exception.DomainEventNotFoundException;
import subota.max.outbox.event.repository.DomainEventRepository;

import static subota.max.outbox.event.DomainEventStatus.PUBLISHED;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPublishedEventHandler implements DomainEventHandler<DomainEvent> {
    DomainEventRepository domainEventRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleEvent(DomainEvent event) {
        DomainEventEntity domainEventEntity = domainEventRepository.findById(event.getId())
            .orElseThrow(() -> new DomainEventNotFoundException("Domain event not found " + event.getId()));
        domainEventEntity.setStatus(PUBLISHED);
    }
}
