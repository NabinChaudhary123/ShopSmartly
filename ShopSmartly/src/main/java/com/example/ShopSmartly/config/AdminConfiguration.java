package com.example.ShopSmartly.config;

import com.example.ShopSmartly.entity.Role;
import com.example.ShopSmartly.entity.UserEntity;
import com.example.ShopSmartly.repository.RoleRepository;
import com.example.ShopSmartly.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AdminConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initailizeAdmin(){

        return args ->{
            String defaultAdminEmail = "administratorSS@gmail.com";
            String defaultAdminPassword = "default@admin.com";

            if(userRepository.findByEmail(defaultAdminEmail).isEmpty()){
                UserEntity admin = new UserEntity();
                admin.setEmail(defaultAdminEmail);
                admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
                Role roles = roleRepository.findByName("ROLE_ADMIN").orElseThrow(()-> new RuntimeException("Role does not exist"));
                admin.setRole(roles);

                userRepository.save(admin);

            }
        };
    }
}
