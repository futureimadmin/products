package com.futureim.voicecommerce.product.api.dto;

import com.futureim.voicecommerce.product.model.Category;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private boolean active;
    private Long parentId;
    private List<CategoryDto> subcategories;
    private int productCount;

    public static CategoryDto fromEntity(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setDisplayOrder(category.getDisplayOrder());
        dto.setActive(category.isActive());
        
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        
        if (category.getSubcategories() != null) {
            dto.setSubcategories(category.getSubcategories().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList()));
        }
        
        dto.setProductCount(category.getProducts().size());
        
        return dto;
    }

    public static Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setDisplayOrder(dto.getDisplayOrder());
        category.setActive(dto.isActive());
        
        if (dto.getParentId() != null) {
            Category parent = new Category();
            parent.setId(dto.getParentId());
            category.setParent(parent);
        }
        
        return category;
    }
}
