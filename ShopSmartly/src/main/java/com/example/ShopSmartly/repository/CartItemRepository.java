package com.example.ShopSmartly.repository;

import com.example.ShopSmartly.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByUserIdAndProductIdAndOrderId(Long userId, Long productId, Long orderId);

    List<CartItems> findByUserIdAndOrderId(Long userId, Long orderId);
}
