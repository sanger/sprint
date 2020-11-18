package uk.ac.sanger.sprint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config component to allow CORS from specific origins
 * @author dr6
 */
@Component
@Configuration
public class CorsConfigurer implements WebMvcConfigurer {
    @Value("${sprint.cors-origins}")
    private String[] origins;

    public String[] getOrigins() {
        return this.origins;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/graphql").allowedOrigins(getOrigins()).allowCredentials(true);
    }
}
