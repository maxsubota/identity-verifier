package subota.max.notification.infrastructure.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import subota.max.notification.infrastructure.property.GotifyApplicationProperty;

public class FeignClientConfig {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(GotifyApplicationProperty applicationProperty) {
        return new BasicAuthRequestInterceptor(applicationProperty.username(), applicationProperty.password());
    }
}
