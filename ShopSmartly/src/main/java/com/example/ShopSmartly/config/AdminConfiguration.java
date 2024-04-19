package com.example.ShopSmartly.config;

import com.example.ShopSmartly.entity.Role;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AdminConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initailizeAdmin(){

        return args ->{
            String defaultAdminEmail = "admin@gmail.com";
            String defaultAdminPassword = "default@admin.com";

            if(userRepository.findByEmail(defaultAdminEmail).isEmpty()){
                UserEntity admin = new UserEntity();
                admin.setEmail(defaultAdminEmail);
                admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

            }
        };
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
