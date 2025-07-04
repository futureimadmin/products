package com.futureim.generative.im.mcp.communication;

import com.futureim.generative.im.mcp.MCPServer;
import com.futureim.generative.im.model.Agent;

import java.util.Map;

public abstract class AgentCommunicationPattern {
    protected final MCPServer server;
    protected final Agent agent;
    
    public AgentCommunicationPattern(MCPServer server, Agent agent) {
        this.server = server;
        this.agent = agent;
    }
    
    public abstract void sendTo(String receiverId, String message);
    
    public abstract void broadcast(String message);
    
    public abstract void requestInformation(String receiverId, String request);
    
    public abstract void respondToRequest(String senderId, String response);
    
    protected Map<String, Object> createMessage(String type, String content) {
        return Map.of(
            "type", type,
            "content", content,
            "sender", agent.getAgentId(),
            "timestamp", System.currentTimeMillis()
        );
    }
}
