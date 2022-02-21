package com.intrum.creditmanagementservice.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    private static final String BASIC_SCHEME = "basicScheme";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(BASIC_SCHEME))
                .components(
                        new Components()
                                .addSecuritySchemes(BASIC_SCHEME,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")))
                .info(new Info().title("Credit management service REST API")
                        .description("Intrum credit management service")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Site to Intrum documentation")
                        .url("https://www.intrum.com/"));
    }
}
