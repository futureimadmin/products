package com.futureim.generative.im.supervisor;

import com.futureim.generative.im.model.AgentType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
@RequiredArgsConstructor
@Slf4j
public class SupervisorAgent implements SupervisorResource {
    private final String resourceId = UUID.randomUUID().toString();
    private String status = "READY";
    private double progress = 0.0;
    private Map<String, Object> properties = new ConcurrentHashMap<>();
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private final Map<String, List<String>> agentTasks = new ConcurrentHashMap<>();
    
    @Override
    public String getResourceId() {
        return resourceId;
    }
    
    @Override
    public void receiveMessage(String senderId, String message) {
        try {
            if (senderId.equals("user")) {
                processPrompt(message);
            } else {
                // Handle responses from agents
                handleAgentResponse(senderId, message);
            }
        } catch (Exception e) {
            log.error("Error receiving message from {}: {}", senderId, e.getMessage());
            setStatus("ERROR");
            setProgress(0.0);
        }
    }
    
    private void processPrompt(String prompt) {
        try {
            // Analyze prompt and determine required agents
            List<String> requiredAgents = analyzePrompt(prompt);
            
            // Create and send requests to required agents
            for (String agentType : requiredAgents) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("prompt", prompt);
                parameters.put("agentType", agentType);
                parameters.put("timestamp", Instant.now().toString());
                
                // Send to Kafka topic
                kafkaTemplate.send(getAgentTopic(agentType), parameters.toString());
            }
        } catch (Exception e) {
            log.error("Error processing prompt: {}", prompt, e);
            setStatus("ERROR");
            setProgress(0.0);
        }
    }
    
    private List<String> analyzePrompt(String prompt) {
        // TODO: Implement prompt analysis logic
        // For now, return a hardcoded list of agents
        return new ArrayList<>(List.of("requirements", "architecture", "coding"));
    }
    
    private String getAgentTopic(String agentType) {
        String lowercaseType = agentType.toLowerCase();
        switch (lowercaseType) {
            case "architecture":
                return "architecture-commands";
            case "coding":
                return "coding-commands";
            case "deployment":
                return "deployment-commands";
            case "design":
                return "design-commands";
            case "infrastructure":
                return "infrastructure-commands";
            case "quality":
                return "quality-commands";
            case "requirements":
                return "requirements-commands";
            case "security":
                return "security-commands";
            case "testing":
                return "testing-commands";
            default:
                throw new IllegalArgumentException("Unknown agent type: " + agentType);
        }
    }
    
    private void handleAgentResponse(String agentType, String response) {
        // TODO: Implement response handling logic
        // For now, just log the response
        System.out.println("Received response from " + agentType + ": " + response);
    }
    
    @Override
    public void executeTask(String taskId, Map<String, Object> parameters) {
        try {
            String taskType = parameters.get("taskType").toString();
            switch (taskType) {
                case "monitor-project":
                    monitorProject(parameters);
                    break;
                case "coordinate-agents":
                    coordinateAgents(parameters);
                    break;
                case "analyze-prompt":
                    processPrompt(parameters.get("prompt").toString());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type: " + taskType);
            }
        } catch (Exception e) {
            log.error("Error executing task {}: {}", taskId, e.getMessage());
            setStatus("ERROR");
            setProgress(0.0);
        }
    }
    
    @Override
    public void monitorProject(Map<String, Object> parameters) {
        log.info("Monitoring project with parameters: {}", parameters);
        setStatus("MONITORING");
    }
    
    @Override
    public void coordinateAgents(Map<String, Object> parameters) {
        log.info("Coordinating agents with parameters: {}", parameters);
        setStatus("COORDINATING");
    }
    
    @Override
    public void updateStatus(String status) {
        this.status = status;
    }
    
    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    @Override
    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }

    @Override
    public void updateProperties(Map<String, Object> properties) {
        this.properties.putAll(properties);
    }

    @Override
    public void setAgentType(AgentType agentType) {

    }

    @Override
    public void setRequirementsText(String requirements) {

    }
}
