package subota.max.verification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "subota.max.*")
@EntityScan(basePackages = "subota.max.*")
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class VerificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerificationApplication.class, args);
	}

}
