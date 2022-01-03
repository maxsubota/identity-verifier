package subota.max.outbox;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "subota.max.outbox.*")
public class OutboxEventAutoConfiguration {
}
