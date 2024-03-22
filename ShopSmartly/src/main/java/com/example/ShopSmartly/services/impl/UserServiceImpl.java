package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.Role;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.RoleRepository;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<String> RegisterUser(UserRegistrationDto userRegistrationDto) {
        if(userRepository.existsByEmail(userRegistrationDto.getEmail())){
            return new ResponseEntity<>("User already exist: "+userRegistrationDto.getEmail(), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setFullName(userRegistrationDto.getFullName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setContact(userRegistrationDto.getContact());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER").orElseThrow(()->new RuntimeException("Such Role does not exist"));
        user.setRole(roles);

        userRepository.save(user);
        return new ResponseEntity<>("User is registered successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserEntity>> ListAllUser() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }
}
