package com.futureim.generative.im.claude;

import com.futureim.generative.im.claude.model.ClaudeResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClaudeServiceImpl implements ClaudeService {
    private final ClaudeClient claudeClient;
    private final Gson gson;

    @Autowired
    public ClaudeServiceImpl(ClaudeClient claudeClient) {
        this.claudeClient = claudeClient;
        this.gson = new Gson();
    }

    @Override
    public Map<String, Object> analyzePrompt(String prompt) {
        // Use Claude to analyze the prompt and determine required agents
        String promptText = "Analyze this prompt and determine the required SDLC phases:\n" + prompt + "\n" +
                           "Return a JSON array of agent types required for this project";
        
        ClaudeResponse response = claudeClient.sendMessage(promptText);
        String completion = response.getCompletion();
        List<String> agents = gson.fromJson(completion, List.class);
        return Map.of(
            "agents", agents,
            "timestamp", System.currentTimeMillis()
        );
    }

    @Override
    public Map<String, Object> generateAgentInstructions(String prompt, String agentType) {
        // Generate detailed instructions for the agent
        String instructions = "Generate detailed instructions for the " + agentType + " agent:\n" + 
                            "Project context: " + prompt + "\n" +
                            "Include specific tasks, deliverables, and success criteria";
        
        ClaudeResponse response = claudeClient.sendMessage(instructions);
        return Map.of(
            "instructions", response.getCompletion(),
            "timestamp", System.currentTimeMillis()
        );
    }

    @Override
    public Map<String, Object> analyzeRequirements(String requirements) {
        // Use Claude to analyze requirements
        String prompt = "Analyze these requirements:\n" + requirements + "\n" +
                        "Identify key components, dependencies, and potential issues";
        
        ClaudeResponse response = claudeClient.sendMessage(prompt);
        return Map.of(
            "analysis", response.getCompletion(),
            "timestamp", System.currentTimeMillis()
        );
    }

    @Override
    public Map<String, Object> generateSpecification(String requirements) {
        // Use Claude to generate specification
        String prompt = "Create a detailed specification based on these requirements:\n" + requirements + "\n" +
                        "Include: technical requirements, data models, API specifications, and UI/UX guidelines";
        
        ClaudeResponse response = claudeClient.sendMessage(prompt);
        return Map.of(
            "specification", response.getCompletion(),
            "timestamp", System.currentTimeMillis()
        );
    }

    @Override
    public Map<String, Object> createUserStories(String requirements) {
        // Use Claude to create user stories
        String prompt = "Create user stories based on these requirements: \"" + requirements + "\"\n" +
                        "Format each story as:\n" +
                        "As a [user role]\n" +
                        "I want [feature]\n" +
                        "So that [benefit]\n" +
                        "Include acceptance criteria for each story\n" +
                        "Format the response as JSON array of stories";
        
        ClaudeResponse response = claudeClient.sendMessage(prompt);
        return Map.of(
            "userStories", response.getCompletion(),
            "timestamp", System.currentTimeMillis()
        );
    }

    @Override
    public Map<String, Object> generateRequirementsInstructions(String prompt) {
        // Generate initialization instructions for requirements analysis
        String instructions = "Analyze the following prompt and prepare for requirements gathering:\n" +
                            prompt + "\n" +
                            "Focus on:\n" +
                            "1. Identifying key requirements\n" +
                            "2. Creating user stories\n" +
                            "3. Generating specification\n" +
                            "4. Identifying dependencies and risks";
        
        ClaudeResponse response = claudeClient.sendMessage(instructions);
        return Map.of(
            "instructions", response.getCompletion(),
            "timestamp", System.currentTimeMillis()
        );
    }
}
