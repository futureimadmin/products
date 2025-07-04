package com.futureim.voicecommerce.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VoiceCommerceCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoiceCommerceCartApplication.class, args);
    }
}
