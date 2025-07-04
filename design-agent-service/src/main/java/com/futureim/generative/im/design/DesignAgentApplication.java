package com.futureim.generative.im.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DesignAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignAgentApplication.class, args);
    }
}
