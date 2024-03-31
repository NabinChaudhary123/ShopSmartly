package com.example.ShopSmartly.repository;

import com.example.ShopSmartly.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
