package com.futureim.voicecommerce.order.domain.event;

import com.futureim.voicecommerce.order.domain.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEvent {
    private String cartId;
    private String userId;
    private CartEventType eventType;
    private CartItem item;
    private LocalDateTime timestamp;

    public enum CartEventType {
        ITEM_ADDED,
        ITEM_REMOVED,
        QUANTITY_UPDATED,
        CART_CLEARED,
        CART_CHECKED_OUT
    }
}
