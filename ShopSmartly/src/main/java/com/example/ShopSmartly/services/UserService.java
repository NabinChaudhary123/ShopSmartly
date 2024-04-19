package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<String> RegisterUser(UserRegistrationDto userRegistrationDto);
    ResponseEntity<List<UserEntity>> ListAllUser();
    UserEntity getUserById(Long userId);
    ResponseEntity<?>updateUser(Long userId, UserEntity user);
}
