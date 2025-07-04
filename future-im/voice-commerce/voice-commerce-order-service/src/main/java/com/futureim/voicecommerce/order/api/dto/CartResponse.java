package com.futureim.voicecommerce.order.api.dto;

import com.futureim.voicecommerce.order.domain.model.Cart;
import com.futureim.voicecommerce.order.domain.model.CartStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CartResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> items;
    private CartStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal totalAmount;

    public static CartResponse fromCart(Cart cart) {
        return CartResponse.builder()
            .id(cart.getId())
            .userId(cart.getUserId())
            .items(cart.getItems().stream()
                .map(CartItemResponse::fromCartItem)
                .collect(Collectors.toList()))
            .status(cart.getStatus())
            .createdAt(cart.getCreatedAt())
            .updatedAt(cart.getUpdatedAt())
            .totalAmount(cart.getTotalAmount())
            .build();
    }
}
