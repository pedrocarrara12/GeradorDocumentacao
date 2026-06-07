package com.pedrocarrara.doctechai.client;

import com.pedrocarrara.doctechai.config.GeminiApiProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class GeminiClient {

    private final WebClient geminiWebClient;
    private final GeminiApiProperties geminiApiProperties;

    public GeminiClient(WebClient geminiWebClient, GeminiApiProperties geminiApiProperties) {
        this.geminiWebClient = geminiWebClient;
        this.geminiApiProperties = geminiApiProperties;
    }

    public String gerarConteudo(String prompt) {
        GeminiRequest request = new GeminiRequest(
                List.of(new Content(List.of(new Part(prompt))))
        );

        GeminiResponse response = geminiWebClient.post()
                .uri("/v1beta/models/{model}:generateContent", geminiApiProperties.model())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        return extrairTexto(response);
    }

    private String extrairTexto(GeminiResponse response) {
        return Optional.ofNullable(response)
                .map(GeminiResponse::candidates)
                .filter(candidates -> !candidates.isEmpty())
                .map(candidates -> candidates.getFirst())
                .map(Candidate::content)
                .map(Content::parts)
                .filter(parts -> !parts.isEmpty())
                .map(parts -> parts.getFirst())
                .map(Part::text)
                .orElseThrow(() -> new IllegalStateException("Gemini nao retornou texto na resposta."));
    }

    private record GeminiRequest(List<Content> contents) {
    }

    private record GeminiResponse(List<Candidate> candidates) {
    }

    private record Candidate(Content content) {
    }

    private record Content(List<Part> parts) {
    }

    private record Part(String text) {
    }
}
