package com.example.ShopSmartly.dto;

import com.example.ShopSmartly.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
