package com.futureim.generative.im.supervisor.mcp;

import com.futureim.generative.im.mcp.MCPResource;
import com.futureim.generative.im.supervisor.SupervisorAgent;
import com.futureim.generative.im.supervisor.SupervisorResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SupervisorMCPServer {
    private final Map<String, MCPResource> resources;
    private final SupervisorAgent supervisorAgent;
    
    public SupervisorMCPServer(SupervisorAgent supervisorAgent) {
        this.resources = new ConcurrentHashMap<>();
        this.supervisorAgent = supervisorAgent;
        registerResource(supervisorAgent);
    }
    
    public void registerResource(MCPResource resource) {
        if (resource instanceof SupervisorResource) {
            resources.put(resource.getResourceId(), resource);
        } else {
            throw new IllegalArgumentException("Resource must implement SupervisorResource");
        }
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
    
    public void handleProjectMonitoringRequest(Map<String, Object> parameters) {
        supervisorAgent.monitorProject(parameters);
    }
    
    public void handleAgentCoordinationRequest(Map<String, Object> parameters) {
        supervisorAgent.coordinateAgents(parameters);
    }
}
