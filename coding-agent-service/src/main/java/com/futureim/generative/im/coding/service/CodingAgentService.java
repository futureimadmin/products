package com.futureim.generative.im.coding.service;

import com.futureim.generative.im.coding.CodingAgent;
import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CodingAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String CODING_TOPIC = "coding-events";

    public void writeCode(String requirements) {
        // Create coding agent
        CodingAgent agent = new CodingAgent();
        agent.setAgentType(AgentType.CODING);
        agent.setCodeBase(requirements);
        agent.setStatus("WRITING_CODE");

        // Send event to write code
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.CODING);
        event.setEventType("WRITE_CODE");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(CODING_TOPIC, event);
    }

    public void refactorCode(String code) {
        // Create coding agent
        CodingAgent agent = new CodingAgent();
        agent.setAgentType(AgentType.CODING);
        agent.setCodeBase(code);
        agent.setStatus("REFACTORING_CODE");

        // Send event to refactor code
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.CODING);
        event.setEventType("REFACTOR_CODE");
        event.setTimestamp(Instant.now());
        event.setPayload(code);

        kafkaTemplate.send(CODING_TOPIC, event);
    }

    public void generateDocumentation(String code) {
        // Create coding agent
        CodingAgent agent = new CodingAgent();
        agent.setAgentType(AgentType.CODING);
        agent.setCodeBase(code);
        agent.setStatus("GENERATING_DOCS");

        // Send event to generate documentation
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.CODING);
        event.setEventType("GENERATE_DOCUMENTATION");
        event.setTimestamp(Instant.now());
        event.setPayload(code);

        kafkaTemplate.send(CODING_TOPIC, event);
    }

    public void performCodeReview(String code) {
        // Create coding agent
        CodingAgent agent = new CodingAgent();
        agent.setAgentType(AgentType.CODING);
        agent.setCodeBase(code);
        agent.setStatus("PERFORMING_REVIEW");

        // Send event to perform code review
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.CODING);
        event.setEventType("PERFORM_CODE_REVIEW");
        event.setTimestamp(Instant.now());
        event.setPayload(code);

        kafkaTemplate.send(CODING_TOPIC, event);
    }
}
