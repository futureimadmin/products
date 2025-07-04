package com.futureim.generative.im.projectplanning.service;

import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import com.futureim.generative.im.projectplanning.ProjectPlanningAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProjectPlanningAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String PROJECT_PLANNING_TOPIC = "project-planning-events";

    public void generateProjectPlan(String requirements) {
        // Create project planning agent
        ProjectPlanningAgent agent = new ProjectPlanningAgent();
        agent.setAgentType(AgentType.PROJECT_PLANNING);
        agent.setProjectRequirements(requirements);
        agent.setStatus("PLANNING");

        // Send event to generate project plan
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.PROJECT_PLANNING);
        event.setEventType("GENERATE_PROJECT_PLAN");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(PROJECT_PLANNING_TOPIC, event);
    }

    public void createJiraBoard(String projectKey) {
        // Create Jira board configuration
        ProjectPlanningAgent agent = new ProjectPlanningAgent();
        agent.setAgentType(AgentType.PROJECT_PLANNING);
        agent.setJiraBoardConfig("{\"projectKey\": \"\" + projectKey + \"}");
        agent.setStatus("CREATING_BOARD");

        // Send event to create Jira board
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.PROJECT_PLANNING);
        event.setEventType("CREATE_JIRA_BOARD");
        event.setTimestamp(Instant.now());
        event.setPayload("{\"projectKey\": \"\" + projectKey + \"}");

        kafkaTemplate.send(PROJECT_PLANNING_TOPIC, event);
    }

    public void createEpics(String projectKey) {
        // Create epics
        ProjectPlanningAgent agent = new ProjectPlanningAgent();
        agent.setAgentType(AgentType.PROJECT_PLANNING);
        agent.setEpics("[" + projectKey + ": Epic 1, " + projectKey + ": Epic 2]");
        agent.setStatus("CREATING_EPICS");

        // Send event to create epics
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.PROJECT_PLANNING);
        event.setEventType("CREATE_EPICS");
        event.setTimestamp(Instant.now());
        event.setPayload("{\"projectKey\": \"\" + projectKey + \"}");

        kafkaTemplate.send(PROJECT_PLANNING_TOPIC, event);
    }
}
