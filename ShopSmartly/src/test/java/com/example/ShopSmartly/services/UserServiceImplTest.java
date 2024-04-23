package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.UserRegistrationDto;
import com.example.ShopSmartly.repository.OrderRepository;
import com.example.ShopSmartly.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        userRegistrationDto.setFullName("John Doe");
        userRegistrationDto.setEmail("john@example.com");
        userRegistrationDto.setContact(1234567890L);
        userRegistrationDto.setPassword("password");

        // Execute the method to test
        ResponseEntity<String> responseEntity = userService.RegisterUser(userRegistrationDto);

        // Verify the user is registered successfully
        assertEquals("User is registered successfully", responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the user and order are saved in the database
        assertTrue(userRepository.existsByEmail("john@example.com"));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Prepare test data
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("john@example.com");
        userRegistrationDto.setPassword("password");


        // Execute the method to test
        ResponseEntity<String> responseEntity = userService.RegisterUser(userRegistrationDto);

        // Verify user already exists response
        assertEquals("User already exist: john@example.com", responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    

}
