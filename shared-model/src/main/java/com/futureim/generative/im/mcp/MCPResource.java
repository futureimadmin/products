package com.futureim.generative.im.mcp;

import com.futureim.generative.im.model.AgentType;

import java.util.Map;

public interface MCPResource {
    String getResourceId();
    void receiveMessage(String senderId, String message);
    void executeTask(String taskId, Map<String, Object> parameters);
    void updateStatus(String status);
    void setProperties(Map<String, Object> properties);
    Map<String, Object> getProperties();

    void updateProperties(Map<String, Object> properties);

    void setAgentType(AgentType agentType);

    void setRequirementsText(String requirements);
}
