package com.futureim.voicecommerce.order.api.dto;

import com.futureim.voicecommerce.order.domain.model.CartItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemResponse {
    private String id;
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subtotal;

    public static CartItemResponse fromCartItem(CartItem item) {
        return CartItemResponse.builder()
            .id(item.getId())
            .productId(item.getProductId())
            .productName(item.getProductName())
            .unitPrice(item.getUnitPrice())
            .quantity(item.getQuantity())
            .subtotal(item.getSubtotal())
            .build();
    }
}
