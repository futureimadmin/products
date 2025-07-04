package com.futureim.generative.im.supervisor.controller;

import com.futureim.generative.im.model.Agent;
import com.futureim.generative.im.model.Customer;
import com.futureim.generative.im.supervisor.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SupervisorController {
    @Autowired
    private SupervisorService supervisorService;

    // Accepts customer prompt and creates agents dynamically
    @PostMapping("/prompt")
    public ResponseEntity<String> handlePrompt(@RequestParam Long customerId, @RequestBody String prompt) {
        supervisorService.processPrompt(customerId, prompt);
        return ResponseEntity.ok("Agents created and tasks delegated");
    }

    // List all agents for a customer
    @GetMapping("/customers/{id}/agents")
    public ResponseEntity<List<Agent>> getAgentsForCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(supervisorService.getAgentsForCustomer(id));
    }

    // List all customers (for admin/demo)
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(supervisorService.getAllCustomers());
    }



    // Update agent status
    @PostMapping("/agents/{agentId}/status")
    public ResponseEntity<String> updateAgentStatus(
            @PathVariable String agentId,
            @RequestParam String status) {
        supervisorService.updateAgentStatus(agentId, status);
        return ResponseEntity.ok("Status updated for agent " + agentId);
    }
}
