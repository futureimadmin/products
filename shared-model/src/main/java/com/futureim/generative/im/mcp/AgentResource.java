package com.futureim.generative.im.mcp;

import com.futureim.generative.im.model.Agent;
import com.google.gson.Gson;

import java.util.Map;

public abstract class AgentResource implements MCPResource {
    protected final Agent agent;
    protected final MCPServer agentServer;
    protected final String agentType;
    protected final Gson gson;
    
    public AgentResource(Agent agent, MCPServer agentServer) {
        this.agent = agent;
        this.agentServer = agentServer;
        this.agentType = agent.getAgentType().name().toString();
        this.gson = new Gson();
    }
    
    @Override
    public String getResourceId() {
        return agent.getAgentId().toString();
    }
    
    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
            "agentType", agentType,
            "status", agent.getStatus(),
            "customerId", agent.getCustomer().getCustomerId(),
            "lastUpdated", System.currentTimeMillis()
        );
    }
    
    @Override
    public void setProperties(Map<String, Object> properties) {
        if (properties.containsKey("status")) {
            String status = (String) properties.get("status");
            agent.setStatus(status);
            updateStatus(status);
        }
        if (properties.containsKey("progress")) {
            Double progress = (Double) properties.get("progress");
            agentServer.updateProgress(getResourceId(), progress);
        }
    }
    
    @Override
    public void executeTask(String taskId, Map<String, Object> parameters) {
        switch (taskId) {
            case "initialize":
                initializeAgent(parameters);
                break;
            case "processTask":
                processTask(parameters);
                break;
            case "handleMessage":
                handleMessage(parameters);
                break;
            default:
                throw new IllegalArgumentException("Unknown task: " + taskId);
        }
    }
    
    protected abstract void initializeAgent(Map<String, Object> parameters);
    protected abstract void processTask(Map<String, Object> parameters);
    protected abstract void handleMessage(Map<String, Object> message);
    
    @Override
    public void receiveMessage(String senderId, String message) {
        Map<String, Object> msg = Map.of(
            "senderId", senderId,
            "content", message
        );
        executeTask("handleMessage", msg);
    }
    
    protected void sendMessage(String receiverId, String message) {
        agentServer.sendMessage(getResourceId(), receiverId, message);
    }
    
    protected void broadcastMessage(String message) {
        agentServer.broadcastMessage(getResourceId(), message);
    }
    
    @Override
    public void updateStatus(String status) {
        agent.setStatus(status);
        agentServer.updateStatus(getResourceId(), status);
    }
    
    protected void updateProgress(double progress) {
        agentServer.updateProgress(getResourceId(), progress);
    }
    
    protected void requestInformation(String receiverId, String request) {
        Map<String, Object> msg = Map.of(
            "type", "REQUEST",
            "content", request
        );
        sendMessage(receiverId, gson.toJson(msg));
    }
    
    protected void respondToRequest(String senderId, String response) {
        Map<String, Object> msg = Map.of(
            "type", "RESPONSE",
            "content", response
        );
        sendMessage(senderId, gson.toJson(msg));
    }
}
