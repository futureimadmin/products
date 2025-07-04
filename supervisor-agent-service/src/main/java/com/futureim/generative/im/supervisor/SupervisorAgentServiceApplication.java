package com.futureim.generative.im.supervisor;

import com.futureim.generative.im.supervisor.mcp.SupervisorMCPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class SupervisorAgentServiceApplication {
    
    @Autowired
    private SupervisorAgent supervisorAgent;
    
    @Bean
    public SupervisorMCPServer supervisorMCPServer() {
        return new SupervisorMCPServer(supervisorAgent);
    }
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SupervisorAgentServiceApplication.class, args);
        
        // Get beans from Spring context
        SupervisorAgent supervisorAgent = context.getBean(SupervisorAgent.class);
        SupervisorMCPServer mcpServer = context.getBean(SupervisorMCPServer.class);
        
        // Register the agent with the MCP server
        mcpServer.registerResource(supervisorAgent);
    }
}
