package com.futureim.generative.im.infrastructure.mcp;

import com.futureim.generative.im.infrastructure.InfrastructureAgent;
import com.futureim.generative.im.mcp.MCPResource;
import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InfrastructureMCPServer {
    private final String serverId;
    private final Map<String, MCPResource> resources;
    private final Gson gson;
    private final InfrastructureAgent infrastructureAgent;
    
    public InfrastructureMCPServer(String serverId, InfrastructureAgent infrastructureAgent) {
        this.serverId = serverId;
        this.resources = new ConcurrentHashMap<>();
        this.gson = new Gson();
        this.infrastructureAgent = infrastructureAgent;
        registerResource(infrastructureAgent);
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
    
    public void handleInfrastructureSetupRequest(Map<String, Object> parameters) {
        infrastructureAgent.setupInfrastructure(parameters);
    }
    
    public void handleInfrastructureValidationRequest(Map<String, Object> parameters) {
        infrastructureAgent.validateInfrastructure(parameters);
    }
}
