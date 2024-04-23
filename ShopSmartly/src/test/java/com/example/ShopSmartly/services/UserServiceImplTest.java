package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Optionally, you can perform any setup here before each test method
    }

    @Test
    public void testRegisterUser_Success() {
        // Prepare test data
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFullName("Johnny Makan");
        userRegistrationDto.setEmail("johnny@example.com");
        userRegistrationDto.setContact(1234567890L);
        userRegistrationDto.setPassword("nabin");

        // Execute the method to test
        ResponseEntity<String> responseEntity = userService.RegisterUser(userRegistrationDto);

        // Verify the user is registered successfully
        assertEquals("User is registered successfully", responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the user and order are saved in the database
        assertTrue(userRepository.existsByEmail("johnny@example.com"));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Prepare test data
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("johnny@example.com");
        userRegistrationDto.setPassword("nabin");


        // Execute the method to test
        ResponseEntity<String> responseEntity = userService.RegisterUser(userRegistrationDto);

        // Verify user already exists response
        assertEquals("User already exist: johnny@example.com", responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUser_Success() {
        // Prepare test data
        UserEntity user = new UserEntity();
        user.setFullName("Rambo");
        user.setEmail("rambo@example.com");
        user.setContact(1234567890L);
        user.setPassword("nabin");
        UserEntity savedUser = userRepository.save(user);

        // Update user data
        savedUser.setFullName("Ram");
        savedUser.setEmail("ram@example.com");

        // Execute the method to test
        ResponseEntity<?> responseEntity = userService.updateUser(savedUser.getId(), savedUser);

        // Verify the updated user
        assertEquals("Ram", userRepository.findById(savedUser.getId()).get().getFullName());
        assertEquals("ram@example.com", userRepository.findById(savedUser.getId()).get().getEmail());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        // Prepare test data
        UserEntity user = new UserEntity();
        user.setId(100L); // User with ID 100 does not exist
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setContact(1234567890L);

        // Execute the method to test and verify that it throws UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(user.getId(), user));
    }

}
