package com.maxx.imperion.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI imperionOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Imperion Student Management API")

                        .version("v1.0.0")

                        .description("""
                                A production-ready Student Management REST API
                                built using Spring Boot.

                                Features:
                                • CRUD Operations
                                • DTO Pattern
                                • Bean Validation
                                • ModelMapper
                                • Swagger/OpenAPI
                                • Soft Delete & Restore
                                • Duplicate Email Validation
                                • Course Management
                                • Multiple Subjects
                                • Search APIs
                                • Count APIs
                                """)

                        .contact(new Contact()

                                .name("Made by Maxx")

                                .email("maxx@example.com")

                                .url("https://github.com/madebymaxx/imperion-student-management"))

                        .license(new License()

                                .name("MIT License")

                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()

                        .description("Project Repository")

                        .url("https://github.com/madebymaxx/imperion-student-management"));
    }
}