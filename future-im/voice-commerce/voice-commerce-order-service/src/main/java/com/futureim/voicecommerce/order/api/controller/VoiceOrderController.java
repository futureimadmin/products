package com.futureim.voicecommerce.order.api.controller;

import com.futureim.voicecommerce.order.api.dto.CartResponse;
import com.futureim.voicecommerce.order.domain.model.Cart;
import com.futureim.voicecommerce.order.domain.service.VoiceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/voice-order")
@RequiredArgsConstructor
@Slf4j
public class VoiceOrderController {

    private final VoiceOrderService voiceOrderService;

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getActiveCart(@PathVariable String userId) {
        var cart = voiceOrderService.getActiveCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping(value = "/cart/voice-add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CartResponse> addToCartByVoice(
            @RequestParam("userId") String userId,
            @RequestParam("audioFile") MultipartFile audioFile) {
        try {
            var cart = voiceOrderService.processVoiceOrder(userId, audioFile.getBytes());
            return ResponseEntity.ok(CartResponse.fromCart(cart));
        } catch (IOException e) {
            log.error("Error processing voice command", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cart/{userId}/add")
    public ResponseEntity<CartResponse> addToCart(
            @PathVariable String userId,
            @RequestParam String productId,
            @RequestParam(defaultValue = "1") int quantity) {
        var cart = voiceOrderService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(CartResponse.fromCart(cart));
    }

    @PostMapping("/cart/{userId}/checkout")
    public ResponseEntity<CartResponse> checkout(@PathVariable String userId) {
        var cart = voiceOrderService.checkout(userId);
        return ResponseEntity.ok(CartResponse.fromCart(cart));
    }
}
