package com.just.commitoudesiste.commitoudesiste_backend.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Anti-Golpe Pix Just")
                        .version("v1.0")
                        .description("Documentação da API do sistema antifraude para transações Pix")
                        .contact(new Contact()
                                .name("Maia")
                                .email("joaovictorhmm.contato@gmail.com")
                                .url("https://github.com/vaundyyy")));
    }
}
