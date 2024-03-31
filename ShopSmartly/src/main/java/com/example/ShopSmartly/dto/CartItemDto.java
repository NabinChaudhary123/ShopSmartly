package com.example.ShopSmartly.dto;

import com.example.ShopSmartly.entity.Product;
import lombok.Data;

@Data
public class CartItemDto {

    private Long id;
    private int quantity;
    private double totalPrice;
    private Cart cart;
    private Product product;
}
