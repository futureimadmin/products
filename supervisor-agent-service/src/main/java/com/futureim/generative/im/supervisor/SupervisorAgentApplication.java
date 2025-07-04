package com.futureim.generative.im.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SupervisorAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupervisorAgentApplication.class, args);
    }
}
