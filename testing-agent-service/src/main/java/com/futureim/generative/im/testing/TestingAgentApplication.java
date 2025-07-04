package com.futureim.generative.im.testing;

import com.futureim.generative.im.testing.mcp.TestingMCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestingAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestingAgentApplication.class, args);
        
        // Initialize testing agent and MCP server
        TestingAgent testingAgent = new TestingAgent();
        TestingMCPServer mcpServer = new TestingMCPServer(testingAgent);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(testingAgent);
    }
}
