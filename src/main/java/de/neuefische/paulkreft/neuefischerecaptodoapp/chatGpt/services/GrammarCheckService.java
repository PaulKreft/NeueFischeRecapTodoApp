package de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrammarCheckService {
    private final ChatGptService chatGptService;

    public String correctGrammar(String input) {
        String prompt = String.format("""
                Correct spelling and grammar: %s
                Make sure the statements ends with one period
                """, input);

        return chatGptService.generateResponse(prompt);
    }
}
