package com.futureim.voicecommerce.api.dto;

import com.futureim.voicecommerce.domain.model.Product;
import com.futureim.voicecommerce.domain.model.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductImage primaryImage;
    private Set<ProductImage> additionalImages;
    private boolean inStock;
    private Integer availableQuantity;

    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .primaryImage(product.getPrimaryImage())
            .additionalImages(product.getAdditionalImages())
            .inStock(product.getInventory().getInStock())
            .availableQuantity(product.getInventory().getQuantity())
            .build();
    }
}
