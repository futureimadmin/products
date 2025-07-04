package com.futureim.generative.im.requirements;

import com.futureim.generative.im.requirements.mcp.RequirementsMCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RequirementsAgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequirementsAgentServiceApplication.class, args);
        
        // Initialize requirements agent and MCP server
        RequirementsAgent requirementsAgent = new RequirementsAgent();
        RequirementsMCPServer mcpServer = new RequirementsMCPServer(requirementsAgent);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(requirementsAgent);
    }
}
