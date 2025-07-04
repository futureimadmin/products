package com.futureim.generative.im.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InfrastructureAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfrastructureAgentApplication.class, args);
    }
}
