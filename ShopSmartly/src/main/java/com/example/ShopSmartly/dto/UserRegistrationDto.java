package com.example.ShopSmartly.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {

    private String fullName;
    private String email;
    private Long contact;
    private String password;
}
