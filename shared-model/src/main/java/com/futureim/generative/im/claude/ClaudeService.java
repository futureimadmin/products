package com.futureim.generative.im.claude;

import java.util.Map;

public interface ClaudeService {
    Map<String, Object> analyzePrompt(String prompt);
    Map<String, Object> generateAgentInstructions(String prompt, String agentType);
    Map<String, Object> analyzeRequirements(String requirements);
    Map<String, Object> generateSpecification(String requirements);
    Map<String, Object> createUserStories(String requirements);
    Map<String, Object> generateRequirementsInstructions(String prompt);
}
