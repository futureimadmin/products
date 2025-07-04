package com.futureim.generative.im.supervisor.service;

import com.futureim.generative.im.model.Agent;
import com.futureim.generative.im.model.AgentType;
import com.futureim.generative.im.model.Customer;
import com.futureim.generative.im.monitoring.AgentMonitor;
import com.futureim.generative.im.supervisor.model.AgentEvent;
import com.futureim.generative.im.supervisor.repo.AgentRepository;
import com.futureim.generative.im.supervisor.repo.CustomerRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
@Slf4j
public class SupervisorService {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentMonitor agentMonitor;

    @KafkaListener(topics = "${spring.cloud.stream.bindings.supervisor-input.destination}")
    public void handleIncomingEvent(AgentEvent event) {
        try {
            switch (event.getEventType()) {
                case "ANALYZE_PROMPT":
                    analyzePrompt(event.getPayload());
                    break;
                case "ROUTE_TASK":
                    routeTask(event.getPayload());
                    break;
                case "ORCHESTRATE_AGENTS":
                    orchestrateAgents(event.getPayload());
                    break;
                case "MONITOR_PROGRESS":
                    monitorProgress(event.getPayload());
                    break;
                default:
                    handleUnknownEvent(event);
            }
        } catch (Exception e) {
            log.error("Error handling event: " + event.getEventType(), e);
            throw e;
        }
    }

    private void analyzePrompt(Map<String, Object> payload) {
        Long customerId = (Long) payload.get("customerId");
        String prompt = (String) payload.get("prompt");
        
        if (customerId != null && prompt != null) {
            // Create and register agents
            List<String> requiredAgents = Arrays.asList("requirements", "coding", "testing", "deployment");
            for (String agentType : requiredAgents) {
                createAndRegisterAgent(customerId, agentType, prompt);
            }
        } else {
            log.warn("Invalid analyze prompt payload: " + payload);
        }
    }

    private void routeTask(Map<String, Object> payload) {
        String agentId = (String) payload.get("agentId");
        String taskId = (String) payload.get("taskId");
        Object taskParamsObj = payload.get("taskParams");
        
        if (agentId != null && taskId != null && taskParamsObj instanceof Map) {
            Map<String, Object> taskParams = (Map<String, Object>) taskParamsObj;
            
            // Send task parameters to agent's Kafka topic
            String topic = agentId + "-commands";
            kafkaTemplate.send(topic, new Gson().toJson(taskParams));
        } else {
            log.warn("Invalid route task payload: " + payload);
        }
    }

    private void orchestrateAgents(Map<String, Object> payload) {
        String workflowId = (String) payload.get("workflowId");
        Object agentIdsObj = payload.get("agentIds");
        Object workflowParamsObj = payload.get("workflowParams");
        
        if (workflowId != null && agentIdsObj instanceof List && workflowParamsObj instanceof Map) {
            List<String> agentIds = (List<String>) agentIdsObj;
            Map<String, Object> workflowParams = (Map<String, Object>) workflowParamsObj;
            
            // Send orchestration message to each agent
            for (String agentId : agentIds) {
                String topic = agentId + "-commands";
                kafkaTemplate.send(topic, new Gson().toJson(workflowParams));
            }
        } else {
            log.warn("Invalid orchestrate agents payload: " + payload);
        }
    }

    private void monitorProgress(Map<String, Object> payload) {
        String agentId = (String) payload.get("agentId");
        String status = (String) payload.get("status");
        Double progress = (Double) payload.get("progress");
        
        if (agentId != null && status != null && progress != null) {
            agentMonitor.updateStatus(agentId, status);
            agentMonitor.updateProgress(agentId, progress);
        } else {
            log.warn("Invalid progress update payload: " + payload);
        }
    }

    private void handleUnknownEvent(AgentEvent event) {
        log.warn("Received unknown event type: " + event.getEventType());
    }

    private void createAndRegisterAgent(Long customerId, String agentType, String prompt) {
        // Get customer
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        Customer customer = customerOpt.get();
        
        // Create agent entity
        Agent agent = new Agent();
        agent.setAgentType(AgentType.valueOf(agentType));
        agent.setStatus("CREATED");
        agent.setCustomer(customer);
        
        // Save agent
        agent = agentRepository.save(agent);
        
        // Send initialization message to Kafka
        Map<String, Object> message = new HashMap<>();
        message.put("agentId", agent.getAgentId());
        message.put("customerId", customerId);
        message.put("agentType", agentType);
        message.put("prompt", prompt);
        message.put("timestamp", Instant.now().toString());
        
        // Send to agent-specific Kafka topic
        String topic = agentType.toLowerCase() + "-commands";
        kafkaTemplate.send(topic, new Gson().toJson(message));
        
        // Record task in monitoring
        agentMonitor.recordTask(agent.getAgentId().toString(), "initialize", message);
    }
    public void processPrompt(Long customerId, String prompt) {
        // Create and register agents
        List<String> requiredAgents = Arrays.asList("requirements", "coding", "testing", "deployment");
        
        // Get customer
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        Customer customer = customerOpt.get();
        
        // Create and register agents
        for (String agentType : requiredAgents) {
            // Create agent entity
            Agent agent = new Agent();
            agent.setAgentType(AgentType.valueOf(agentType.toUpperCase()));
            agent.setStatus("CREATED");
            agent.setCustomer(customer);
            
            // Save agent
            agent = agentRepository.save(agent);
            
            // Send initialization message to Kafka
            Map<String, Object> message = new HashMap<>();
            message.put("agentId", agent.getAgentId());
            message.put("customerId", customerId);
            message.put("agentType", agentType);
            message.put("prompt", prompt);
            message.put("timestamp", Instant.now().toString());
            
            // Send to agent-specific Kafka topic
            String topic = agentType.toLowerCase() + "-commands";
            kafkaTemplate.send(topic, new Gson().toJson(message));
            
            // Record task in monitoring
            agentMonitor.recordTask(agent.getAgentId().toString(), "initialize", message);
            
            // Update agent status with required agents
            agent.setRequiredAgents(requiredAgents);
            agentRepository.save(agent);
        }
    }
    
    public List<Agent> getAgentsForCustomer(Long customerId) {
        return agentRepository.findByCustomerCustomerId(customerId);
    }
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public void updateAgentStatus(String agentId, String status) {
        agentMonitor.updateStatus(agentId, status);
    }
    
    public AgentMonitor getAgentMonitor() {
        return agentMonitor;
    }
}
