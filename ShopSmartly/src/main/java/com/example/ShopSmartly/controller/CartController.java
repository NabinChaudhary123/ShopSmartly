package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.CartDto;
import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/postCart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartDto cartDto){
        return cartService.addProductToCart(cartDto);
    }

    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
