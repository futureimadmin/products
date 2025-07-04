package com.futureim.voicecommerce.cart.api.controller;

import com.futureim.voicecommerce.cart.domain.service.VoiceCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/voice-cart")
@RequiredArgsConstructor
@Slf4j
public class VoiceCartController {

    private final VoiceCartService voiceCartService;

    @GetMapping(value = "/{userId}/read", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readCartItems(@PathVariable String userId) {
        byte[] audioResponse = voiceCartService.readCartItems(userId);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(audioResponse);
    }

    @PostMapping(value = "/{userId}/confirm-address", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> confirmShippingAddress(
            @PathVariable String userId,
            @RequestParam("voiceConfirmation") MultipartFile voiceConfirmation) {
        try {
            voiceCartService.confirmShippingAddress(userId, voiceConfirmation.getBytes());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error confirming shipping address", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/{userId}/confirm-order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> confirmOrder(
            @PathVariable String userId,
            @RequestParam("voiceConfirmation") MultipartFile voiceConfirmation) {
        try {
            voiceCartService.confirmOrder(userId, voiceConfirmation.getBytes());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error confirming order", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
