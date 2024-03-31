package com.example.ShopSmartly.dto;

import com.example.ShopSmartly.entity.UserEntity;
import lombok.Data;

import java.util.Set;

@Data
public class CartDto {

    private Long id;

    private Set<CartItemDto> cartItem;

    private UserEntity user;
}
