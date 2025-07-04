package com.futureim.generative.im.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CodingAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodingAgentApplication.class, args);
    }
}
