package com.futureim.voicecommerce.cart.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phoneNumber;
}
