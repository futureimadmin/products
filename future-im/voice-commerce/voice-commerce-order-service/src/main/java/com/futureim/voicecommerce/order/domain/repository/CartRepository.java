package com.futureim.voicecommerce.order.domain.repository;

import com.futureim.voicecommerce.order.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserIdAndStatus(String userId, String status);
}
