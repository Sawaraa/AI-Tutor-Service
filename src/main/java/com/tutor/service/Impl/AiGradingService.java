package com.tutor.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.model.AiResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiGradingService {

    private final ChatClient chatClient;

    public AiGradingService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public AiResult gradeSubmission(String taskDescription, String studentAnswer) {

        String prompt = """
                Ти — асистент викладача. Оціни відповідь студента на завдання.
                
                Завдання: %s
                
                Відповідь студента: %s
                
                Дай відповідь ТІЛЬКИ в такому форматі JSON:
                {
                  "score": (число від 0 до 100),
                  "feedback": "детальний розбір — що студент зрозумів добре і що треба покращити"
                }
                """.formatted(taskDescription, studentAnswer);

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return parseResponse(response);
    }

    private AiResult parseResponse(String response) {
        try {
            // прибираємо ```json якщо є
            String clean = response
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(clean);

            int score = node.get("score").asInt();
            String feedback = node.get("feedback").asText();

            return new AiResult(score, feedback);
        } catch (Exception e) {
            return new AiResult(0, "AI не зміг перевірити відповідь");
        }
    }

}
