package com.futureim.generative.im.coding.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LLMService {
    @Value("${llm.prompts.code}")
    private String codePromptTemplate;

    @Value("${llm.prompts.tests}")
    private String testsPromptTemplate;

    @Value("${llm.prompts.database}")
    private String databasePromptTemplate;

    @Value("${llm.prompts.environment}")
    private String environmentPromptTemplate;

    
    @Value("${llm.api.url}")
    private String llmApiUrl;
    
    @Value("${llm.api.key}")
    private String llmApiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public LLMService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public String generateCode(String requirements, String context) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "code-davinci-002");
        request.put("prompt", createCodeGenerationPrompt(requirements, context));
        request.put("temperature", 0.7);
        request.put("max_tokens", 2000);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + llmApiKey);
        headers.set("Content-Type", "application/json");
        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(
            llmApiUrl + "/completions",
            HttpMethod.POST,
            entity,
            String.class
        );
        
        return extractCodeFromResponse(response.getBody());
    }

    public String generateTests(String requirements, String context) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "code-davinci-002");
        request.put("prompt", createTestGenerationPrompt(requirements, context));
        request.put("temperature", 0.7);
        request.put("max_tokens", 1500);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + llmApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            llmApiUrl + "/completions",
            HttpMethod.POST,
            entity,
            String.class
        );

        return extractCodeFromResponse(response.getBody());
    }

    public String generateDatabaseSetup(String requirements, String context) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "code-davinci-002");
        request.put("prompt", createDatabaseSetupPrompt(requirements, context));
        request.put("temperature", 0.7);
        request.put("max_tokens", 1000);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + llmApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            llmApiUrl + "/completions",
            HttpMethod.POST,
            entity,
            String.class
        );

        return extractCodeFromResponse(response.getBody());
    }

    public String generateEnvironmentSetup(String requirements, String context) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "code-davinci-002");
        request.put("prompt", createEnvironmentSetupPrompt(requirements, context));
        request.put("temperature", 0.7);
        request.put("max_tokens", 1000);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + llmApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            llmApiUrl + "/completions",
            HttpMethod.POST,
            entity,
            String.class
        );

        return extractCodeFromResponse(response.getBody());
    }

    private String createCodeGenerationPrompt(String requirements, String context) {
        return String.format(codePromptTemplate, requirements, context);
    }

    private String createTestGenerationPrompt(String requirements, String context) {
        return String.format(testsPromptTemplate, requirements, context);
    }

    private String createDatabaseSetupPrompt(String requirements, String context) {
        return String.format(databasePromptTemplate, requirements, context);
    }

    private String createEnvironmentSetupPrompt(String requirements, String context) {
        return String.format(environmentPromptTemplate, requirements, context);
    }

    private String extractCodeFromResponse(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode choices = rootNode.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                JsonNode text = choices.get(0).path("text");
                return text.asText();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse LLM response", e);
        }
        return "";
    }
}
