package com.futureim.voicecommerce.product.repository;

import com.futureim.voicecommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId OR p.category.parent.id = :categoryId")
    Page<Product> findByCategoryIdIncludingSubcategories(Long categoryId, Pageable pageable);
    
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stockQuantity > 0")
    Page<Product> findAllAvailable(Pageable pageable);
    
    List<Product> findByBrandAndModelIgnoreCase(String brand, String model);
    
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL ORDER BY p.brand")
    List<String> findAllBrands();
}
