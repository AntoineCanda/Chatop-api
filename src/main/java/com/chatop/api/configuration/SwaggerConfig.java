package com.chatop.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Class that handles Swagger
 */
@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI for the Chatop API.
     *
     * @return the customized OpenAPI object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("token",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Chatop API")
                        .version("1.0.0")
                        .description("Chatop API")
                        .license(new io.swagger.v3.oas.models.info.License()));

    }
}
