package com.futureim.voicecommerce.cart.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class VoiceCart {
    @Id
    private String id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoiceCartItem> items = new ArrayList<>();

    private String userId;
    private CartStatus status;

    @Embedded
    private ShippingAddress shippingAddress;

    @Embedded
    private PaymentInfo paymentInfo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal totalAmount;
    private boolean addressConfirmed;
    private boolean orderConfirmed;

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = CartStatus.ACTIVE;
        this.totalAmount = BigDecimal.ZERO;
        this.addressConfirmed = false;
        this.orderConfirmed = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addItem(VoiceCartItem item) {
        items.add(item);
        recalculateTotal();
    }

    public void removeItem(VoiceCartItem item) {
        items.remove(item);
        recalculateTotal();
    }

    public void updateItemQuantity(String productId, int quantity) {
        items.stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst()
            .ifPresent(item -> {
                item.setQuantity(quantity);
                recalculateTotal();
            });
    }

    private void recalculateTotal() {
        this.totalAmount = items.stream()
            .map(VoiceCartItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmAddress() {
        this.addressConfirmed = true;
    }

    public void confirmOrder() {
        this.orderConfirmed = true;
        this.status = CartStatus.CONFIRMED;
    }

    public void processPayment() {
        this.status = CartStatus.PAYMENT_PROCESSING;
    }

    public void completePayment() {
        this.status = CartStatus.COMPLETED;
    }

    public void failPayment() {
        this.status = CartStatus.PAYMENT_FAILED;
    }
}
