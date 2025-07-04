package com.futureim.voicecommerce.order.domain.service;

import com.futureim.voicecommerce.order.domain.event.CartEvent;
import com.futureim.voicecommerce.order.domain.model.Cart;
import com.futureim.voicecommerce.order.domain.model.CartItem;
import com.futureim.voicecommerce.order.domain.model.CartStatus;
import com.futureim.voicecommerce.order.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoiceOrderService {

    private final CartRepository cartRepository;
    private final VoiceConversionService voiceConversionService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Cart processVoiceOrder(String userId, byte[] audioData) {
        String command = voiceConversionService.convertVoiceToText(audioData);
        log.info("Processed voice command: {}", command);

        // Extract product information from voice command
        // This would be more sophisticated in a real implementation
        String[] parts = command.toLowerCase().split(" ");
        String productId = parts[parts.length - 1];
        int quantity = 1;

        return addToCart(userId, productId, quantity);
    }

    @Transactional
    public Cart addToCart(String userId, String productId, int quantity) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        // In a real implementation, we would fetch product details from a product service
        // item.setProductName(productService.getProductName(productId));
        // item.setUnitPrice(productService.getProductPrice(productId));
        
        cart.addItem(item);
        cart = cartRepository.save(cart);

        publishEvent(cart, CartEvent.CartEventType.ITEM_ADDED, item);
        
        return cart;
    }

    @Transactional
    public Cart checkout(String userId) {
        Cart cart = getActiveCart(userId);
        cart.checkout();
        cart = cartRepository.save(cart);
        
        publishEvent(cart, CartEvent.CartEventType.CART_CHECKED_OUT, null);
        
        return cart;
    }

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE.name())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                return cartRepository.save(newCart);
            });
    }

    public Cart getActiveCart(String userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE.name())
            .orElseThrow(() -> new RuntimeException("No active cart found for user"));
    }

    private void publishEvent(Cart cart, CartEvent.CartEventType eventType, CartItem item) {
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
