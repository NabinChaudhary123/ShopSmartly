package com.example.ShopSmartly.dto;

import lombok.Data;

@Data
public class CartDto {

    private Long id;
    private Long price;
    private Long quantity;
    private Long productId;
    private Long orderId;
    private String productName;
    private byte [] returnedImage;
    private Long userId;
}
