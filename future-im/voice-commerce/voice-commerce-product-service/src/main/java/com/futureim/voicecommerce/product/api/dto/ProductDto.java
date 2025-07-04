package com.futureim.voicecommerce.product.api.dto;

import com.futureim.voicecommerce.product.model.Category;
import com.futureim.voicecommerce.product.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String brand;
    private String model;
    private String sku;
    private int stockQuantity;
    private boolean active;
    private Long categoryId;
    private String categoryName;
    private List<String> imageUrls = new ArrayList<>();

    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setBrand(product.getBrand());
        dto.setModel(product.getModel());
        dto.setSku(product.getSku());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setActive(product.isActive());
        
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        
        dto.setImageUrls(new ArrayList<>(product.getImageUrls()));
        
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        product.setBrand(dto.getBrand());
        product.setModel(dto.getModel());
        product.setSku(dto.getSku());
        product.setStockQuantity(dto.getStockQuantity());
        product.setActive(dto.isActive());
        
        if (dto.getCategoryId() != null) {
            var category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }
        Set<String> images = new HashSet<>();
        images.addAll(dto.getImageUrls());
        product.setImageUrls(images);
        
        return product;
    }
}
