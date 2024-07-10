package com.compass.ecommerce.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .version("1.0")
                        .description("API para gerenciar um sistema de e-commerce")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Natan Neves")
                                .email("dev.natanbarbosa.com")
                                .url("https://github.com/NatanNeves"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
