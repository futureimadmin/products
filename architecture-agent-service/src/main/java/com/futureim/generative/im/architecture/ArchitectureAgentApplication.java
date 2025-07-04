package com.futureim.generative.im.architecture;

import com.futureim.generative.im.architecture.mcp.ArchitectureMCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ArchitectureAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArchitectureAgentApplication.class, args);
        
        // Initialize architecture agent and MCP server
        ArchitectureAgent architectureAgent = new ArchitectureAgent();
        ArchitectureMCPServer mcpServer = new ArchitectureMCPServer("",architectureAgent);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(architectureAgent);
    }
}
