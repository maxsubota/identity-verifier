package subota.max.notification.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import subota.max.notification.infrastructure.property.QueuesProperty;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue domainEventQueue(QueuesProperty queuesProperty) {
        return new Queue(queuesProperty.verification());
    }
}
