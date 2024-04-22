package com.example.ShopSmartly.controller;

import com.example.ShopSmartly.dto.LoginDto;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.JWTGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto, HttpServletResponse response) throws IOException, JSONException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),loginDto.getPassword()));
            UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()->
                    new UsernameNotFoundException("User does not exist"));
            String token = jwtGenerator.generateToken(user);
            JSONObject jsonResponse = new JSONObject()
                    .put("userId", user.getId())
                    .put("role",user.getRole())
                    .put("token",token);

            HttpHeaders headers = new HttpHeaders();
            response.addHeader(HEADER_STRING,TOKEN_PREFIX + token);
            return new ResponseEntity<>(jsonResponse.toString(), headers,HttpStatus.OK);

        }
        catch (BadCredentialsException e){
//            throw new BadCredentialsException("Incorrect username or password");
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }

    }
}
