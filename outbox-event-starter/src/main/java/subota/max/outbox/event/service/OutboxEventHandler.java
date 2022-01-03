package subota.max.outbox.event.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import subota.max.core.event.DomainEvent;
import subota.max.core.event.DomainEventHandler;
import subota.max.outbox.event.DomainEventEntity;
import subota.max.outbox.event.repository.DomainEventRepository;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxEventHandler implements DomainEventHandler<DomainEvent> {

    DomainEventRepository domainEventRepository;

    @Override
    @Transactional
    public void handleEvent(DomainEvent event) {
        DomainEventEntity domainEventEntity = DomainEventEntity.of(event);
        domainEventRepository.save(domainEventEntity);
    }
}
