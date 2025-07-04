package com.futureim.voicecommerce.domain.repository;

import com.futureim.voicecommerce.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           ":keyword MEMBER OF p.searchKeywords")
    List<Product> findByVoiceSearch(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM Product p " +
           "WHERE EXISTS (SELECT 1 FROM p.searchKeywords k " +
           "WHERE LOWER(k) IN :keywords)")
    List<Product> findByKeywords(@Param("keywords") List<String> keywords);
}
