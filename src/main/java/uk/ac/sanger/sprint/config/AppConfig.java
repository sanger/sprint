package uk.ac.sanger.sprint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * @author dr6
 */
@Configuration
public class AppConfig {
    @Value("${sprint.volume}")
    private String volume;

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    public String getVolume() {
        return this.volume;
    }
}
