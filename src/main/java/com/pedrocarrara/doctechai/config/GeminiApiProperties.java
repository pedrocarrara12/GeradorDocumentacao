package com.pedrocarrara.doctechai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gemini.api")
public record GeminiApiProperties(
        String baseUrl,
        String key,
        String model
) {
}
