package com.futureim.generative.im.architecture.service;

import com.futureim.generative.im.architecture.ArchitectureAgent;
import com.futureim.generative.im.architecture.mcp.ArchitectureMCPServer;
import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArchitectureAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private final ArchitectureMCPServer mcpServer;
    private static final String ARCHITECTURE_TOPIC = "architecture-events";

    public void designArchitecture(String requirements) {
        // Create architecture agent
        ArchitectureAgent agent = new ArchitectureAgent();
        agent.setAgentType(AgentType.ARCHITECTURE);
        agent.setRequirementsText(requirements);

        // Register and initialize the agent with MCP server
        mcpServer.registerResource(agent);
        mcpServer.handleArchitectureDesignRequest(Map.of("requirements", requirements));
        agent.setStatus("DESIGNING_ARCHITECTURE");

        // Send event to design architecture
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.ARCHITECTURE);
        event.setEventType("DESIGN_ARCHITECTURE");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(ARCHITECTURE_TOPIC, event);
    }

    public void selectTechnologyStack(String requirements) {
        // Create architecture agent
        ArchitectureAgent agent = new ArchitectureAgent();
        agent.setAgentType(AgentType.ARCHITECTURE);
        agent.setRequirementsText(requirements);
        agent.setStatus("SELECTING_TECH_STACK");

        // Send event to select technology stack
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.ARCHITECTURE);
        event.setEventType("SELECT_TECH_STACK");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(ARCHITECTURE_TOPIC, event);
    }

    public void createDeploymentArchitecture(String requirements) {
        // Create architecture agent
        ArchitectureAgent agent = new ArchitectureAgent();
        agent.setAgentType(AgentType.ARCHITECTURE);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_DEPLOYMENT_ARCH");

        // Send event to create deployment architecture
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.ARCHITECTURE);
        event.setEventType("CREATE_DEPLOYMENT_ARCH");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(ARCHITECTURE_TOPIC, event);
    }

    public void createScalabilityPlan(String requirements) {
        // Create architecture agent
        ArchitectureAgent agent = new ArchitectureAgent();
        agent.setAgentType(AgentType.ARCHITECTURE);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_SCALABILITY_PLAN");

        // Send event to create scalability plan
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.ARCHITECTURE);
        event.setEventType("CREATE_SCALABILITY_PLAN");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(ARCHITECTURE_TOPIC, event);
    }

    public void createSecurityArchitecture(String requirements) {
        // Create architecture agent
        ArchitectureAgent agent = new ArchitectureAgent();
        agent.setAgentType(AgentType.ARCHITECTURE);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_SECURITY_ARCH");

        // Send event to create security architecture
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.ARCHITECTURE);
        event.setEventType("CREATE_SECURITY_ARCH");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(ARCHITECTURE_TOPIC, event);
    }
}
