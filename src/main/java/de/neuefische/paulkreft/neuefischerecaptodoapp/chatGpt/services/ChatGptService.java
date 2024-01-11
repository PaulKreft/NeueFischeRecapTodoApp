package de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.services;

import de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.models.ChatGptMessage;
import de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.models.ChatGptRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.models.ChatGptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service

public class ChatGptService {

    private final RestClient restClient;

    public ChatGptService(@Value("${app.chatgpt.api.url}") String url,
                          @Value("${app.chatgpt.api.key}") String apiKey,
                          @Value("${app.chatgpt.api.org}") String org) {

        restClient = RestClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("OpenAI-Organization", org)
                .build();
    }

    public String generateResponse(String message) {

        ChatGptRequest requestBody = new ChatGptRequest(List.of(new ChatGptMessage("user", message)), "gpt-3.5-turbo");

        ChatGptResponse response = restClient.post()
                .uri("/completions")
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ChatGptResponse.class);

        return response.choices().getFirst().message().content();
    }
}
