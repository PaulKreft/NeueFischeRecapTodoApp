package de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.models;

import java.util.List;

public record ChatGptResponse(
        List<ChatGptChoice> choices
) {
}
