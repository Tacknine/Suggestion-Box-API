package com.suggestion_box.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.info.title:Suggestion Box API}")
    private String title;

    @Value("${springdoc.info.description:API ya kusimamia maoni ya wateja TISEZA}")
    private String description;

    @Value("${springdoc.info.version:1.0.0}")
    private String version;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name("JACKSON HARUNI PETRO")
                                .email("harunijackson504@gmail.com")
                                .url("https://github.com/yourusername"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://suggestion-box-api.onrender.com")
                                .description("🌐 Production Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("💻 Development Server")
                ));
    }
}