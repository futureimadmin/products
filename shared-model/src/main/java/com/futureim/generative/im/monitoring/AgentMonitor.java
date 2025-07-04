package com.futureim.generative.im.monitoring;

import com.futureim.generative.im.model.AgentStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AgentMonitor {
    private final Map<String, AgentStatus> agentStatuses = new ConcurrentHashMap<>();
    
    public void updateStatus(String agentId, String status) {
        agentStatuses.compute(agentId, (id, current) -> {
            if (current == null) {
                current = new AgentStatus();
            }
            current.setStatus(status);
            current.setLastUpdated(Instant.now());
            return current;
        });
    }
    
    public void recordTask(String agentId, String taskId, Map<String, Object> parameters) {
        agentStatuses.compute(agentId, (id, current) -> {
            if (current == null) {
                current = new AgentStatus();
            }
            // Store task information in status
            current.setStatus("Task: " + taskId);
            return current;
        });
    }
    
    public AgentStatus getAgentStatus(String agentId) {
        return agentStatuses.get(agentId);
    }
    
    public Map<String, AgentStatus> getAllAgentStatuses() {
        return new ConcurrentHashMap<>(agentStatuses);
    }
    
    public void updateProgress(String agentId, double progress) {
        agentStatuses.compute(agentId, (id, current) -> {
            if (current == null) {
                current = new AgentStatus();
            }
            current.setProgress(progress);
            current.setLastUpdated(Instant.now());
            return current;
        });
    }

    public void clearStatus(String agentId) {
        agentStatuses.remove(agentId);
    }
}
