package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.CartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(CartDto cartDto);

}
