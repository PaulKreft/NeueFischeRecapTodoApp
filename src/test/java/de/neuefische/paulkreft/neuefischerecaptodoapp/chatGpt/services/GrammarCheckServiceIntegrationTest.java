package de.neuefische.paulkreft.neuefischerecaptodoapp.chatGpt.services;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class GrammarCheckServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static MockWebServer mockWebServer;

    static {
        System.setProperty("CHATGPT_API_KEY", "someKey");
        System.setProperty("CHATGPT_API_ORG", "someOrg");
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        System.out.println(mockWebServer.url(""));
        registry.add("app.chatgpt.api.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void correctGrammar() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                        {
                          "id": "chatcmpl-123",
                          "object": "chat.completion",
                          "created": 1677652288,
                          "model": "gpt-3.5-turbo-0613",
                          "system_fingerprint": "fp_44709d6fcb",
                          "choices": [{
                            "index": 0,
                            "message": {
                              "role": "assistant",
                              "content": "Chillen"
                            },
                            "logprobs": null,
                            "finish_reason": "stop"
                          }],
                          "usage": {
                            "prompt_tokens": 9,
                            "completion_tokens": 12,
                            "total_tokens": 21
                          }
                        }
                        """)
                .addHeader("Content-Type", "application/json"));

        mockMvc.perform(post("/api/ai/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "description": "Shoping",
                                   "status": "OPEN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "description": "Chillen",
                        "status": "OPEN"
                        }
                        """));
    }
}
