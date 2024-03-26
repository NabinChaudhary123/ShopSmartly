package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.AuthResponseDto;
import com.example.ShopSmartly.dto.LoginDto;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginDto loginDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),loginDto.getPassword()));
            UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()->
                    new UsernameNotFoundException("User does not exist"));
            String token = jwtGenerator.generateToken(user);
            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>(new AuthResponseDto("Invalid credentials"),HttpStatus.UNAUTHORIZED);
        }

    }
}
