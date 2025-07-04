package com.futureim.generative.im.mcp.communication;

import com.futureim.generative.im.mcp.MCPServer;
import com.futureim.generative.im.model.Agent;
import com.google.gson.Gson;

import java.util.Map;

public class SequentialCommunication extends AgentCommunicationPattern {
    private final MCPServer server;
    private final Agent agent;
    private final Gson gson;

    public SequentialCommunication(MCPServer server, Agent agent) {
        super(server, agent);
        this.server = server;
        this.agent = agent;
        this.gson = new Gson();
    }
    
    @Override
    public void sendTo(String receiverId, String message) {
        Map<String, Object> msg = createMessage("SEQUENTIAL", message);
        server.sendMessage(agent.getAgentId().toString(), receiverId, gson.toJson(msg));
    }
    
    @Override
    public void broadcast(String message) {
        // Sequential pattern doesn't support broadcast
        throw new UnsupportedOperationException("Sequential pattern doesn't support broadcast");
    }
    
    @Override
    public void requestInformation(String receiverId, String request) {
        Map<String, Object> msg = createMessage("REQUEST", request);
        server.sendMessage(agent.getAgentId().toString(), receiverId, gson.toJson(msg));
    }
    
    @Override
    public void respondToRequest(String senderId, String response) {
        Map<String, Object> msg = createMessage("RESPONSE", response);
        server.sendMessage(agent.getAgentId().toString(), senderId, gson.toJson(msg));
    }
}
