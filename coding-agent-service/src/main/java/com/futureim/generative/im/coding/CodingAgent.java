package com.futureim.generative.im.coding;

import com.futureim.generative.im.coding.dto.WorkflowNotification;
import com.futureim.generative.im.coding.workflow.CodingWorkflow;
import com.futureim.generative.im.mcp.MCPResource;
import com.futureim.generative.im.model.Agent;
import com.futureim.generative.im.model.AgentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CodingAgent implements MCPResource {
    private static final String RESOURCE_ID = "coding-agent";
    private static final String JAVA_PROJECT_TEMPLATE = "src/main/resources/templates/java-project";

    @Autowired
    private Agent agent;
    @Autowired
    private CodingWorkflow workflow;

    @Override
    public String getResourceId() {
        return RESOURCE_ID;
    }

    @Override
    public void receiveMessage(String senderId, String message) {
        try {
            // Extract requirements from message
            String requirements = message.split("::")[1];

            // Execute the workflow with requirements
            workflow.executeWorkflow(requirements);

            // Notify supervisor and testing agents after workflow completion
            notifySupervisorAndTestingAgents(RESOURCE_ID);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void executeTask(String taskId, Map<String, Object> parameters) {
        
    }

    @Override
    public void updateStatus(String status) {

    }

    @Override
    public void setProperties(Map<String, Object> properties) {

    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public void updateProperties(Map<String, Object> properties) {

    }

    private void implementRequirements(String requirements) throws IOException {
        // Execute the workflow asynchronously
        workflow.executeWorkflow(requirements);
    }

    private static final String SUPERVISOR_TOPIC = "supervisor.notifications";
    private static final String TESTING_TOPIC = "testing.notifications";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private void notifySupervisorAndTestingAgents(String projectId) {
        String workflowId = UUID.randomUUID().toString();
        String timestamp = Instant.now().toString();

        WorkflowNotification notification = new WorkflowNotification();
        notification.setWorkflowId(workflowId);
        notification.setStatus("COMPLETED");
        notification.setTimestamp(timestamp);
        notification.setProjectId(projectId);
        notification.setMessage("Workflow completed successfully");

        // Notify Supervisor
        CompletableFuture<SendResult<String, Object>> supervisorFuture =
                kafkaTemplate.send(SUPERVISOR_TOPIC, projectId, notification);

        supervisorFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send supervisor notification: " + ex.getMessage());
            } else {
                System.out.println("Successfully sent supervisor notification");
            }
        });

        // Notify Testing Agent
        CompletableFuture<SendResult<String, Object>> testingFuture =
                kafkaTemplate.send(TESTING_TOPIC, projectId, notification);

        supervisorFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send supervisor notification: " + ex.getMessage());
            } else {
                System.out.println("Successfully sent supervisor notification");
            }
        });
    }

    public void setAgentType(AgentType agentType) {
    }

    @Override
    public void setRequirementsText(String requirements) {

    }

    public void setCodeBase(String requirements) {
    }

    public void setStatus(String writingCode) {
    }
}
