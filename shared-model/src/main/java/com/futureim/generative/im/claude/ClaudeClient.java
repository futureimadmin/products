package com.futureim.generative.im.claude;

import com.futureim.generative.im.claude.model.ClaudeResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class ClaudeClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson;
    private String apiKey;
    private static final String API_URL = "https://api.anthropic.com/v1/completions";

    @Value("${claude.api.key}")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ClaudeClient() {
        this.gson = new Gson();
    }

    public ClaudeResponse sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);

        String json = gson.toJson(Map.of(
            "model", "claude-2",
            "prompt", message,
            "max_tokens", 1000,
            "temperature", 0.7
        ));

        String response = restTemplate.postForObject(
            API_URL,
            new HttpEntity<>(json, headers),
            String.class
        );

        return gson.fromJson(response, ClaudeResponse.class);
    }

    public String generateInstructions(String prompt, String agentType) {
        String message = "You are a system prompt engineer. Generate detailed instructions for a " + 
            agentType + " agent.\n" +
            "The agent will use these instructions to perform its tasks.\n" +
            "Project context: " + prompt + "\n" +
            "Include:\n" +
            "1. Clear task description\n" +
            "2. Required inputs and outputs\n" +
            "3. Success criteria\n" +
            "4. Error handling guidelines\n" +
            "Format the response as JSON object";
        
        ClaudeResponse response = sendMessage(message);
        return response.getCompletion();
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public Map<String, Object> parseResponse(String json) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
