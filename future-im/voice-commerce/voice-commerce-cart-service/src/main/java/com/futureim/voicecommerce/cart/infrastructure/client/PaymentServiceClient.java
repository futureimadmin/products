package com.futureim.voicecommerce.cart.infrastructure.client;

import com.futureim.voicecommerce.cart.domain.model.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentServiceClient {
    
    @PostMapping("/api/v1/payments/process")
    void processPayment(
        @RequestParam("cartId") String cartId,
        @RequestBody PaymentInfo paymentInfo,
        @RequestParam("amount") BigDecimal amount
    );
}
