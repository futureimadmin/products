package com.futureim.voicecommerce.product.repository;

import com.futureim.voicecommerce.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllRootCategories();
    
    List<Category> findByParentId(Long parentId);
    
    Optional<Category> findByName(String name);
    
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subcategories WHERE c.parent IS NULL")
    List<Category> findAllRootCategoriesWithSubcategories();
    
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id")
    Optional<Category> findByIdWithProducts(Long id);
}
