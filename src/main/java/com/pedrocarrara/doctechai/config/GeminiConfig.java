package com.pedrocarrara.doctechai.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(GeminiApiProperties.class)
public class GeminiConfig {

    @Bean
    public WebClient geminiWebClient(GeminiApiProperties geminiApiProperties) {
        return WebClient.builder()
                .baseUrl(geminiApiProperties.baseUrl())
                .defaultHeader("x-goog-api-key", geminiApiProperties.key())
                .build();
    }
}
