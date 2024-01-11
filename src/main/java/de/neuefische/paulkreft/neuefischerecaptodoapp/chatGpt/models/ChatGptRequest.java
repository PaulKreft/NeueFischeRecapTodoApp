package de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.models;

import java.util.List;

public record ChatGptRequest(
        List<ChatGptMessage> messages,
        String model
) {
}
