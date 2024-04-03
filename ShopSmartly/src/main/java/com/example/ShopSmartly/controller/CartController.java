package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.CartDto;
import com.example.ShopSmartly.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/postCart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartDto cartDto){
        return cartService.addProductToCart(cartDto);
    }
}
