package com.example.backend.config;

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
    public OpenAPI applicationOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Benefícios")
                        .description("API REST para gerenciamento de benefícios e transferência de saldo entre benefícios")
                        .version("v1.0.0")
                        .contact(new Contact().name("Equipe de Desenvolvimento").email("dev@example.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do projeto")
                        .url("https://github.com/rafa-cipri-puti/bip-teste-integrado"));
    }
}
