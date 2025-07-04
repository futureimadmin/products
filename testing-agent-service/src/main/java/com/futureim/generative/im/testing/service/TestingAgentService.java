package com.futureim.generative.im.testing.service;

import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.event.AgentEvent;
import com.futureim.generative.im.testing.TestingAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TestingAgentService {
    private final KafkaTemplate<String, AgentEvent> kafkaTemplate;
    private static final String TESTING_TOPIC = "testing-events";

    public void createTestCases(String requirements) {
        // Create testing agent
        TestingAgent agent = new TestingAgent();
        agent.setAgentType(AgentType.TESTING);
        agent.setTestCases(requirements);
        agent.setStatus("CREATING_TEST_CASES");

        // Send event to create test cases
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.TESTING);
        event.setEventType("CREATE_TEST_CASES");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(TESTING_TOPIC, event);
    }

    public void createTestScenarios(String requirements) {
        // Create testing agent
        TestingAgent agent = new TestingAgent();
        agent.setAgentType(AgentType.TESTING);
        agent.setTestScenarios(requirements);
        agent.setStatus("CREATING_SCENARIOS");

        // Send event to create test scenarios
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.TESTING);
        event.setEventType("CREATE_TEST_SCENARIOS");
        event.setTimestamp(Instant.now());
        event.setPayload(requirements);

        kafkaTemplate.send(TESTING_TOPIC, event);
    }

    public void executeTests(String testSuite) {
        // Create testing agent
        TestingAgent agent = new TestingAgent();
        agent.setAgentType(AgentType.TESTING);
        agent.setTestCases(testSuite);
        agent.setStatus("EXECUTING_TESTS");

        // Send event to execute tests
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.TESTING);
        event.setEventType("EXECUTE_TESTS");
        event.setTimestamp(Instant.now());
        event.setPayload(testSuite);

        kafkaTemplate.send(TESTING_TOPIC, event);
    }

    public void generateTestReport(String testResults) {
        // Create testing agent
        TestingAgent agent = new TestingAgent();
        agent.setAgentType(AgentType.TESTING);
        agent.setTestResults(testResults);
        agent.setStatus("GENERATING_REPORT");

        // Send event to generate test report
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.TESTING);
        event.setEventType("GENERATE_TEST_REPORT");
        event.setTimestamp(Instant.now());
        event.setPayload(testResults);

        kafkaTemplate.send(TESTING_TOPIC, event);
    }

    public void analyzeTestCoverage(String testResults) {
        // Create testing agent
        TestingAgent agent = new TestingAgent();
        agent.setAgentType(AgentType.TESTING);
        agent.setTestCoverage(testResults);
        agent.setStatus("ANALYZING_COVERAGE");

        // Send event to analyze test coverage
        AgentEvent event = new AgentEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setAgentType(AgentType.TESTING);
        event.setEventType("ANALYZE_COVERAGE");
        event.setTimestamp(Instant.now());
        event.setPayload(testResults);

        kafkaTemplate.send(TESTING_TOPIC, event);
    }
}
