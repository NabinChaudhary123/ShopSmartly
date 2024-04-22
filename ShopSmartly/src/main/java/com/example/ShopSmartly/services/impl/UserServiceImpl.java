package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.Order;
import com.example.ShopSmartly.entity.OrderStatus;
import com.example.ShopSmartly.entity.Role;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.UserRepository;
import com.example.ShopSmartly.services.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
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
        user.setRole(Role.USER);
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.Pending);
        order.setPrice(0L);
        order.setTotalAmount(0L);
        orderRepository.save(order);
        return new ResponseEntity<>("User is registered successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserEntity>> ListAllUser() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<?>updateUser(Long userId, UserEntity user){
        UserEntity existingUser = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found with id: "+userId));

        existingUser.setId(userId);
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setContact(user.getContact());

        return ResponseEntity.ok(userRepository.save(existingUser));
    }


}
