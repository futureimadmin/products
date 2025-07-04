package com.futureim.voicecommerce.cart.domain.service;

import com.futureim.voicecommerce.cart.domain.event.CartEvent;
import com.futureim.voicecommerce.cart.domain.model.*;
import com.futureim.voicecommerce.cart.infrastructure.client.OrderServiceClient;
import com.futureim.voicecommerce.cart.infrastructure.client.PaymentServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoiceCartService {

    private final OrderServiceClient orderServiceClient;
    private final VoiceConversionService voiceConversionService;
    private final TextToSpeechService textToSpeechService;
    private final PaymentServiceClient paymentServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public byte[] readCartItems(String userId) {
        VoiceCart cart = orderServiceClient.getActiveCart(userId);
        String cartDescription = buildCartDescription(cart);
        return textToSpeechService.convertTextToSpeech(cartDescription);
    }

    @Transactional
    public void confirmShippingAddress(String userId, byte[] voiceConfirmation) {
        String confirmation = voiceConversionService.convertVoiceToText(voiceConfirmation);
        if (isConfirmationPositive(confirmation)) {
            VoiceCart cart = orderServiceClient.getActiveCart(userId);
            cart.confirmAddress();
            publishEvent(cart, CartEvent.CartEventType.ADDRESS_CONFIRMED, null);
        }
    }

    @Transactional
    public void confirmOrder(String userId, byte[] voiceConfirmation) {
        String confirmation = voiceConversionService.convertVoiceToText(voiceConfirmation);
        if (isConfirmationPositive(confirmation)) {
            VoiceCart cart = orderServiceClient.getActiveCart(userId);
            cart.confirmOrder();
            publishEvent(cart, CartEvent.CartEventType.ORDER_CONFIRMED, null);
            processPayment(cart);
        }
    }

    private void processPayment(VoiceCart cart) {
        cart.processPayment();
        publishEvent(cart, CartEvent.CartEventType.PAYMENT_INITIATED, null);

        try {
            paymentServiceClient.processPayment(cart.getId(), cart.getPaymentInfo(), cart.getTotalAmount());
            cart.completePayment();
            publishEvent(cart, CartEvent.CartEventType.PAYMENT_COMPLETED, null);
            publishEvent(cart, CartEvent.CartEventType.INVENTORY_UPDATED, null);
        } catch (Exception e) {
            log.error("Payment processing failed for cart: {}", cart.getId(), e);
            cart.failPayment();
            publishEvent(cart, CartEvent.CartEventType.PAYMENT_FAILED, null);
            throw new RuntimeException("Payment processing failed", e);
        }
    }

    private String buildCartDescription(VoiceCart cart) {
        StringBuilder description = new StringBuilder();
        description.append("Here are the items in your cart: ");
        
        for (VoiceCartItem item : cart.getItems()) {
            description.append(item.getProductName())
                .append(", Quantity: ")
                .append(item.getQuantity())
                .append(", Price: $")
                .append(item.getSubtotal())
                .append(". ");
        }

        description.append("Total amount is $")
            .append(cart.getTotalAmount())
            .append(". Would you like to confirm this order?");

        return description.toString();
    }

    private boolean isConfirmationPositive(String confirmation) {
        String normalizedConfirmation = confirmation.toLowerCase().trim();
        return normalizedConfirmation.contains("yes") || 
               normalizedConfirmation.contains("confirm") || 
               normalizedConfirmation.contains("okay") ||
               normalizedConfirmation.contains("sure");
    }

    private void publishEvent(VoiceCart cart, CartEvent.CartEventType eventType, VoiceCartItem item) {
        CartEvent event = new CartEvent(
            cart.getId(),
            cart.getUserId(),
            eventType,
            item,
            LocalDateTime.now()
        );
        eventPublisher.publishEvent(event);
    }
}
