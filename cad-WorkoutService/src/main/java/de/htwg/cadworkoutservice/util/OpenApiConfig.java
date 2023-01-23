package de.htwg.cadworkoutservice.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(new Info()
                .title("Workout")
                .version("1.0.0")
                .description("Workout Service")
        );
    }
}
