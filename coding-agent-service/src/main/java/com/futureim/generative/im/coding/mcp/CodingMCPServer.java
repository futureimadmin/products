package com.futureim.generative.im.coding.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futureim.generative.im.coding.CodingAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CodingMCPServer {
    @Autowired
    private CodingAgent codingAgent;

    @Autowired
    private ObjectMapper objectMapper;
    
    // Listen to supervisor-to-coding topic and delegate to CodingAgent.receiveMessage
    @KafkaListener(topics = "supervisor-to-coding", groupId = "coding-agent-group")
    public void handleSupervisorEvent(String message) {
        try {
            // Pass the raw message and a senderId to CodingAgent
            codingAgent.receiveMessage("supervisor-agent", message);
        } catch (Exception e) {
            // Log or handle error
            System.err.println("Failed to process supervisor event: " + e.getMessage());
        }
    }
}

