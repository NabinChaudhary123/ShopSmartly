package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.CartDto;
import com.example.ShopSmartly.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(CartDto cartDto);
    OrderDto getCartByUserId(Long userId);

}
