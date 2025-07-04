package com.futureim.voicecommerce.product.api.dto;

import lombok.Data;

@Data
public class BrandDto {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private boolean active;
}
