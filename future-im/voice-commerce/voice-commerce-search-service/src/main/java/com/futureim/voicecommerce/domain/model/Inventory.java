package com.futureim.voicecommerce.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Integer quantity;
    private String sku;
    private Boolean inStock;
}
