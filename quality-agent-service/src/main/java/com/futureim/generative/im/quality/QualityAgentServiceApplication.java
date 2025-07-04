package com.futureim.generative.im.quality;

import com.futureim.generative.im.quality.mcp.QualityMCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QualityAgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(QualityAgentServiceApplication.class, args);
        
        // Initialize quality agent and MCP server
        QualityAgent qualityAgent = new QualityAgent();
        QualityMCPServer mcpServer = new QualityMCPServer(qualityAgent);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(qualityAgent);
    }
}
