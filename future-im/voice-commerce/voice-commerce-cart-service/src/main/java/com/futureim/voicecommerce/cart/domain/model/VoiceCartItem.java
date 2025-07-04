package com.futureim.voicecommerce.cart.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class VoiceCartItem {
    @Id
    private String id;
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subtotal;

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID().toString();
        calculateSubtotal();
    }

    public void calculateSubtotal() {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
