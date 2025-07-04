package com.futureim.voicecommerce.cart.infrastructure.client;

import com.futureim.voicecommerce.cart.domain.model.VoiceCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderServiceClient {
    
    @GetMapping("/api/v1/voice-order/cart/{userId}")
    VoiceCart getActiveCart(@PathVariable("userId") String userId);
}
