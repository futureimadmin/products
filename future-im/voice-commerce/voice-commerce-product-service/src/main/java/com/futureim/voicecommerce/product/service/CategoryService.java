package com.futureim.voicecommerce.product.service;

import com.futureim.voicecommerce.product.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllRootCategories();
    
    List<Category> getAllRootCategoriesWithSubcategories();
    
    List<Category> getSubcategories(Long parentId);
    
    Optional<Category> getCategoryById(Long id);
    
    Optional<Category> getCategoryWithProducts(Long id);
    
    Category createCategory(Category category);
    
    Category updateCategory(Long id, Category category);
    
    void deleteCategory(Long id);
    
    boolean existsById(Long id);
    
    List<Category> getCategoryPath(Long categoryId);
    
    Page<Category> searchCategories(String query, Pageable pageable);
}
