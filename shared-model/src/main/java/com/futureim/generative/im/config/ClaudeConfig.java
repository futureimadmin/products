package com.futureim.generative.im.config;

import com.futureim.generative.im.claude.ClaudeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClaudeConfig {
    @Bean
    public ClaudeClient claudeClient() {
        return new ClaudeClient();
    }
}
