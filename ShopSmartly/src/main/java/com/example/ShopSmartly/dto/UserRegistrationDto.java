package com.example.ShopSmartly.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {

    private String fullName;
    private String email;
    private Integer contact;
    private String password;
}
