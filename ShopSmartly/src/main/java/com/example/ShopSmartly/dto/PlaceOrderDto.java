package com.example.ShopSmartly.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {

    private Long userId;
    private String address;
    private String description;
    private String payment;
}
