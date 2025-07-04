package com.futureim.generative.im.quality.service;

import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import com.futureim.generative.im.quality.QualityAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QualityAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String QUALITY_TOPIC = "quality-events";

    public void createQualityMetrics(String requirements) {
        // Create quality agent
        QualityAgent agent = new QualityAgent();
        agent.setAgentType(AgentType.QUALITY);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_METRICS");

        // Send event to create quality metrics
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.QUALITY);
        event.setEventType("CREATE_QUALITY_METRICS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(QUALITY_TOPIC, event);
    }

    public void defineQualityStandards(String requirements) {
        // Create quality agent
        QualityAgent agent = new QualityAgent();
        agent.setAgentType(AgentType.QUALITY);
        agent.setRequirementsText(requirements);
        agent.setStatus("DEFINING_STANDARDS");

        // Send event to define quality standards
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.QUALITY);
        event.setEventType("DEFINE_QUALITY_STANDARDS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(QUALITY_TOPIC, event);
    }

    public void createQualityChecklist(String requirements) {
        // Create quality agent
        QualityAgent agent = new QualityAgent();
        agent.setAgentType(AgentType.QUALITY);
        agent.setRequirementsText(requirements);
        agent.setStatus("CREATING_CHECKLIST");

        // Send event to create quality checklist
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.QUALITY);
        event.setEventType("CREATE_QUALITY_CHECKLIST");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(QUALITY_TOPIC, event);
    }

    public void performQualityAnalysis(String data) {
        // Create quality agent
        QualityAgent agent = new QualityAgent();
        agent.setAgentType(AgentType.QUALITY);
        agent.setDataToAnalyze(data);
        agent.setStatus("PERFORMING_ANALYSIS");

        // Send event to perform quality analysis
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.QUALITY);
        event.setEventType("PERFORM_QUALITY_ANALYSIS");
        event.setTimestamp(Instant.now());
        event.setPayload(data);

        kafkaTemplate.send(QUALITY_TOPIC, event);
    }

    public void generateQualityReport(String analysisResults) {
        // Create quality agent
        QualityAgent agent = new QualityAgent();
        agent.setAgentType(AgentType.QUALITY);
        agent.setAnalysisResults(analysisResults);
        agent.setStatus("GENERATING_REPORT");

        // Send event to generate quality report
        AgentEvent event = new AgentEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setAgentType(AgentType.QUALITY);
        event.setEventType("GENERATE_QUALITY_REPORT");
        event.setTimestamp(Instant.now());
        event.setPayload(analysisResults);

        kafkaTemplate.send(QUALITY_TOPIC, event);
    }
}
