package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.Security.JWtGenerator;
import com.example.ShopSmartly.dto.AuthResponseDto;
import com.example.ShopSmartly.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWtGenerator jWtGenerator;

    public AuthController(AuthenticationManager authenticationManager, JWtGenerator jWtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jWtGenerator = jWtGenerator;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<AuthResponseDto>loginUser(@RequestBody UserLoginDto userLoginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDto.getEmail(),userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jWtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
    }
}
