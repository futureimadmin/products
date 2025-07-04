package com.futureim.generative.im.requirements.service;

import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import com.futureim.generative.im.requirements.RequirementsAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RequirementsAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String REQUIREMENTS_TOPIC = "requirements-events";

    public void gatherRequirements(String prompt) {
        // Create requirements agent
        RequirementsAgent agent = new RequirementsAgent();
        agent.setAgentType(AgentType.REQUIREMENTS);
        agent.setRequirementsText(prompt);
        agent.setStatus("GATHERING_REQUIREMENTS");

        // Send event to gather requirements
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.REQUIREMENTS);
        event.setEventType("GATHER_REQUIREMENTS");
        event.setTimestamp(Instant.now());
        event.setPayload(prompt);

        kafkaTemplate.send(REQUIREMENTS_TOPIC, event);
    }

    public void analyzeRequirements(String requirements) {
        // Analyze requirements
        RequirementsAgent agent = new RequirementsAgent();
        agent.setAgentType(AgentType.REQUIREMENTS);
        agent.setRequirementsText(requirements);
        agent.setStatus("ANALYZING_REQUIREMENTS");

        // Send event to analyze requirements
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.REQUIREMENTS);
        event.setEventType("ANALYZE_REQUIREMENTS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(REQUIREMENTS_TOPIC, event);
    }

    public void createUserStories(String requirements) {
        // Create user stories
        RequirementsAgent agent = new RequirementsAgent();
        agent.setAgentType(AgentType.REQUIREMENTS);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_STORIES");

        // Send event to create user stories
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.REQUIREMENTS);
        event.setEventType("CREATE_USER_STORIES");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(REQUIREMENTS_TOPIC, event);
    }

    public void createAcceptanceCriteria(String userStories) {
        // Create acceptance criteria
        RequirementsAgent agent = new RequirementsAgent();
        agent.setAgentType(AgentType.REQUIREMENTS);
        agent.setUserStories(userStories);
        agent.setStatus("CREATING_CRITERIA");

        // Send event to create acceptance criteria
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.REQUIREMENTS);
        event.setEventType("CREATE_ACCEPTANCE_CRITERIA");
        event.setTimestamp(Instant.now());
        event.setPayload(userStories);

        kafkaTemplate.send(REQUIREMENTS_TOPIC, event);
    }
}
