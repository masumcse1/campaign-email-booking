package com.property.campaign.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Smart Agent", version = "1.0"),
        servers = {
                @Server(url = "/", description = "Default Server")
        }
)
public class OpenAPIConfiguration {

    @Value("${campaign.servers.domain}")
    private String domain;

    @Bean
    public OpenApiCustomizer forceHttpsAndDomain() {
        return openApi -> {
            // always use https
            openApi.setServers(List.of(
                    new io.swagger.v3.oas.models.servers.Server()
                            .url("https://" + domain)
                            .description("Production Server")
            ));
        };
    }
}