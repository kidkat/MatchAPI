package com.betting.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kidkat
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Match Service API")
                        .version("1.0.0")
                        .description("API for managing matches and odds")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your@email.com")
                        )
                );
    }
}
