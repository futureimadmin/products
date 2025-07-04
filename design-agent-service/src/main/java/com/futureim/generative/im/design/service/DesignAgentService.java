package com.futureim.generative.im.design.service;

import com.futureim.generative.im.design.DesignAgent;
import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DesignAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String DESIGN_TOPIC = "design-events";

    public void createWireframes(String requirements) {
        // Create design agent
        DesignAgent agent = new DesignAgent();
        agent.setAgentType(AgentType.DESIGN);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_WIREFRAMES");

        // Send event to create wireframes
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.DESIGN);
        event.setEventType("CREATE_WIREFRAMES");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(DESIGN_TOPIC, event);
    }

    public void createMockups(String requirements) {
        // Create design agent
        DesignAgent agent = new DesignAgent();
        agent.setAgentType(AgentType.DESIGN);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_MOCKUPS");

        // Send event to create mockups
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.DESIGN);
        event.setEventType("CREATE_MOCKUPS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(DESIGN_TOPIC, event);
    }

    public void createDesignSystem(String requirements) {
        // Create design agent
        DesignAgent agent = new DesignAgent();
        agent.setAgentType(AgentType.DESIGN);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_DESIGN_SYSTEM");

        // Send event to create design system
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.DESIGN);
        event.setEventType("CREATE_DESIGN_SYSTEM");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(DESIGN_TOPIC, event);
    }

    public void createUIComponents(String requirements) {
        // Create design agent
        DesignAgent agent = new DesignAgent();
        agent.setAgentType(AgentType.DESIGN);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_UI_COMPONENTS");

        // Send event to create UI components
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.DESIGN);
        event.setEventType("CREATE_UI_COMPONENTS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(DESIGN_TOPIC, event);
    }

    public void createStyleGuide(String requirements) {
        // Create design agent
        DesignAgent agent = new DesignAgent();
        agent.setAgentType(AgentType.DESIGN);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_STYLE_GUIDE");

        // Send event to create style guide
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.DESIGN);
        event.setEventType("CREATE_STYLE_GUIDE");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(DESIGN_TOPIC, event);
    }
}
