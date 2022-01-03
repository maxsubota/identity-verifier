package subota.max.outbox.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subota.max.outbox.event.DomainEventEntity;

@Repository
public interface DomainEventRepository extends JpaRepository<DomainEventEntity, String> {

}
