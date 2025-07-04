package com.futureim.voicecommerce.product.service;

import com.futureim.voicecommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    
    Page<Product> getProductsByCategory(Long categoryId, boolean includeSubcategories, Pageable pageable);
    
    Optional<Product> getProductById(Long id);
    
    Product createProduct(Product product);
    
    Product updateProduct(Long id, Product product);
    
    void deleteProduct(Long id);
    
    Page<Product> searchProducts(String query, Pageable pageable);
    
    Page<Product> getAvailableProducts(Pageable pageable);
    
    List<String> getAllBrands();
    
    Page<Product> getProductsByBrand(String brand, Pageable pageable);
    
    boolean existsById(Long id);
    
    void updateStock(Long id, int quantity);
}
