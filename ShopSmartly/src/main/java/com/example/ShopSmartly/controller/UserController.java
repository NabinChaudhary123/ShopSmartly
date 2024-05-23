package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
//       return new ResponseEntity<>(userService.RegisterUser(userRegistrationDto), HttpStatus.CREATED);
        return ResponseEntity.ok(userService.RegisterUser(userRegistrationDto));
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserEntity>> ListAllUsers(){
        return userService.ListAllUser();
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        try{
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist/ Not found");
        }

        }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserEntity user){
        try{
            return new ResponseEntity<>(userService.updateUser(userId, user),HttpStatus.CREATED);
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist/ cannot be updated");
        }

    }

}
