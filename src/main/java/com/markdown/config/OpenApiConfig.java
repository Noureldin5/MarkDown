package com.markdown.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI markdownNotesOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setName("Markdown Notes API");
        contact.setEmail("support@markdownnotes.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Markdown Note-taking API")
                .version("1.0.0")
                .description("RESTful API for managing markdown notes with grammar checking and HTML rendering capabilities. " +
                        "This API allows you to create, read, update, and delete notes, upload markdown files, " +
                        "check grammar using LanguageTool, and render markdown content as HTML.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}

