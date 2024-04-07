package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.AddProductInCartDto;
import com.example.ShopSmartly.dto.CartItemsDto;
import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    ResponseEntity<?> addProductToCart(CartItemsDto cartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);


}
