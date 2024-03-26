package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
       return new ResponseEntity(userService.RegisterUser(userRegistrationDto), HttpStatus.CREATED);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserEntity>> ListAllUsers(){
        return userService.ListAllUser();
    }



}
