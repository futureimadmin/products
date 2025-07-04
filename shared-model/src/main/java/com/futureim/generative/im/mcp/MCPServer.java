package com.futureim.generative.im.mcp;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MCPServer {
    private final String serverId;
    private final Map<String, MCPResource> resources;
    private final Gson gson;
    
    public MCPServer(String serverId) {
        this.serverId = serverId;
        this.resources = new ConcurrentHashMap<>();
        this.gson = new Gson();
    }
    
    public void registerResource(MCPResource resource) {
        resources.put(resource.getResourceId(), resource);
    }
    
    public void unregisterResource(String resourceId) {
        resources.remove(resourceId);
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
    
    public Map<String, Object> getResourceProperties(String resourceId) {
        MCPResource resource = resources.get(resourceId);
        return resource != null ? resource.getProperties() : null;
    }
    
    public void broadcastMessage(String senderId, String message) {
        MCPResource sender = resources.get(senderId);
        if (sender != null) {
            for (Map.Entry<String, MCPResource> entry : resources.entrySet()) {
                if (!entry.getKey().equals(senderId)) {
                    entry.getValue().receiveMessage(senderId, message);
                }
            }
        }
    }
    
    public String getServerId() {
        return serverId;
    }
    
    public String toJson() {
        return gson.toJson(this);
    }
    
    public static MCPServer fromJson(String json) {
        return new Gson().fromJson(json, MCPServer.class);
    }
}
