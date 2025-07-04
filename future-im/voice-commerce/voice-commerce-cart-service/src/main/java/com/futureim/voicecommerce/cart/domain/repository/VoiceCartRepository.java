package com.futureim.voicecommerce.cart.domain.repository;

import com.futureim.voicecommerce.cart.domain.model.CartStatus;
import com.futureim.voicecommerce.cart.domain.model.VoiceCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoiceCartRepository extends JpaRepository<VoiceCart, String> {
    Optional<VoiceCart> findByUserIdAndStatus(String userId, CartStatus status);
}
