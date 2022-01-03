package subota.max.core.event;

public interface DomainEventHandler<T extends DomainEvent> {

    void handleEvent(T event);
}
