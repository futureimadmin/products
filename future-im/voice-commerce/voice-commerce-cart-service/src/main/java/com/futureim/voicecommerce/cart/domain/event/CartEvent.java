package com.futureim.voicecommerce.cart.domain.event;

import com.futureim.voicecommerce.cart.domain.model.VoiceCartItem;
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
    private VoiceCartItem item;
    private LocalDateTime timestamp;

    public enum CartEventType {
        ITEM_ADDED,
        ITEM_REMOVED,
        QUANTITY_UPDATED,
        ADDRESS_CONFIRMED,
        ORDER_CONFIRMED,
        PAYMENT_INITIATED,
        PAYMENT_COMPLETED,
        PAYMENT_FAILED,
        INVENTORY_UPDATED
    }
}
