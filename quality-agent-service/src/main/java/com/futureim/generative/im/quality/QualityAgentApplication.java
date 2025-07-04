package com.futureim.generative.im.quality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QualityAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(QualityAgentApplication.class, args);
    }
}
