package com.example.ShopSmartly.dto;

import lombok.Data;

@Data
public class CartDto {

    private Long productId;
    private byte [] returnedImage;
    private Long userId;
}
