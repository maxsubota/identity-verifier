package subota.max.notification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subota.max.notification.domain.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findAllByDispatchedFalseAndRetryCountGreaterThan(int retryCount);
}
