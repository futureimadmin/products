package com.futureim.voicecommerce.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Map /images/products/** to classpath:/static/products/**
        registry.addResourceHandler("/images/products/**")
                .addResourceLocations("classpath:/static/products/")
                .setCachePeriod(3600)
                .resourceChain(false);

        // Fallback for other static resources
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(false);
    }
}
