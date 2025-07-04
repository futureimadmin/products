package com.futureim.generative.im.design;

import com.futureim.generative.im.design.mcp.DesignMCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DesignAgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignAgentServiceApplication.class, args);
        
        // Initialize design agent and MCP server
        DesignAgent designAgent = new DesignAgent();
        DesignMCPServer mcpServer = new DesignMCPServer(designAgent);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(designAgent);
    }
}
