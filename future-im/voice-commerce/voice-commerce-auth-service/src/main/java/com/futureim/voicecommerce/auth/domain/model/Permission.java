package com.futureim.voicecommerce.auth.domain.model;

import jakarta.persistence.Entity;

public enum Permission {
    // User Management
    USER_CREATE,
    USER_READ,
    USER_UPDATE,
    USER_DELETE,

    // Product Management
    PRODUCT_CREATE,
    PRODUCT_READ,
    PRODUCT_UPDATE,
    PRODUCT_DELETE,

    // Order Management
    ORDER_CREATE,
    ORDER_READ,
    ORDER_UPDATE,
    ORDER_DELETE,

    // Cart Management
    CART_CREATE,
    CART_READ,
    CART_UPDATE,
    CART_DELETE,

    // Payment Management
    PAYMENT_CREATE,
    PAYMENT_READ,
    PAYMENT_UPDATE,
    PAYMENT_DELETE,

    // Role Management
    ROLE_CREATE,
    ROLE_READ,
    ROLE_UPDATE,
    ROLE_DELETE
}
