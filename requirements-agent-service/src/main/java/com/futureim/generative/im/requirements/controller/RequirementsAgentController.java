package com.futureim.generative.im.requirements.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requirements")
public class RequirementsAgentController {
    // Endpoint for status update
    @PostMapping("/status")
    public String updateStatus(@RequestBody String statusPayload) {
        // In a real implementation, parse and update status in DB, notify SupervisorAgent
        return "Status received by RequirementsAgent: " + statusPayload;
    }
    // Endpoint for communication with other agents
    @PostMapping("/communicate")
    public String communicate(@RequestBody String message) {
        // In a real implementation, send message to another agent
        return "Message received by RequirementsAgent: " + message;
    }
}
