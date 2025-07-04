package com.futureim.generative.im.deployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DeploymentAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeploymentAgentApplication.class, args);
    }
}
