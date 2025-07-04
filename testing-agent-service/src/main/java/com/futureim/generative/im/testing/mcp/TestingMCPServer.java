package com.futureim.generative.im.testing.mcp;

import com.futureim.generative.im.mcp.MCPResource;
import com.futureim.generative.im.testing.TestingAgent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestingMCPServer {
    private final Map<String, MCPResource> resources;
    private final TestingAgent testingAgent;
    
    public TestingMCPServer(TestingAgent testingAgent) {
        this.resources = new ConcurrentHashMap<>();
        this.testingAgent = testingAgent;
        registerResource(testingAgent);
    }
    
    public void registerResource(MCPResource resource) {
        resources.put(resource.getResourceId(), resource);
    }
    
    public void sendMessage(String senderId, String receiverId, String message) {
        MCPResource sender = resources.get(senderId);
        MCPResource receiver = resources.get(receiverId);
        
        if (sender != null && receiver != null) {
            receiver.receiveMessage(senderId, message);
        }
    }
    
    public void executeTask(String resourceId, String taskId, Map<String, Object> parameters) {
        MCPResource resource = resources.get(resourceId);
        if (resource != null) {
            resource.executeTask(taskId, parameters);
        }
    }
    
    public void updateStatus(String resourceId, String status) {
        MCPResource resource = resources.get(resourceId);
        if (resource != null) {
            resource.updateStatus(status);
        }
    }
    
    public void updateProgress(String resourceId, double progress) {
        MCPResource resource = resources.get(resourceId);
        if (resource != null) {
            Map<String, Object> properties = Map.of("progress", progress);
            resource.updateProperties(properties);
        }
    }
    
    public void handleTestGenerationRequest(Map<String, Object> parameters) {
        testingAgent.receiveMessage("senderId", "");
    }
    
    public void handleTestExecutionRequest(Map<String, Object> parameters) {
//        testingAgent.executeTests(parameters);
    }
}
