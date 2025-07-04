package com.futureim.generative.im.projectplanning.mcp;

import com.futureim.generative.im.mcp.MCPResource;
import com.futureim.generative.im.projectplanning.ProjectPlanningAgent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectPlanningMCPServer {
    private final Map<String, MCPResource> resources;
    private final ProjectPlanningAgent projectPlanningAgent;
    
    public ProjectPlanningMCPServer(ProjectPlanningAgent projectPlanningAgent) {
        this.resources = new ConcurrentHashMap<>();
        this.projectPlanningAgent = projectPlanningAgent;
        registerResource(projectPlanningAgent);
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
    
    public void handleProjectPlanningRequest(Map<String, Object> parameters) {
        projectPlanningAgent.planProject(parameters);
    }
    
    public void handleProjectSchedulingRequest(Map<String, Object> parameters) {
        projectPlanningAgent.scheduleProject(parameters);
    }
}
