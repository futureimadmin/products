package com.futureim.generative.im.design.mcp;

import com.futureim.generative.im.design.DesignAgent;
import com.futureim.generative.im.mcp.MCPResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DesignMCPServer {
    private final Map<String, MCPResource> resources;
    private final DesignAgent designAgent;
    
    public DesignMCPServer(DesignAgent designAgent) {
        this.resources = new ConcurrentHashMap<>();
        this.designAgent = designAgent;
        registerResource(designAgent);
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
            resource.setProperties(properties);
        }
    }
    
    public void handleDesignRequest(Map<String, Object> parameters) {
        designAgent.design(parameters);
    }
    
    public void handleDesignReviewRequest(Map<String, Object> parameters) {
        designAgent.reviewDesign(parameters);
    }
}
