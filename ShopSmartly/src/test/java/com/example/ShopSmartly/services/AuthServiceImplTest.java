package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.LoginDto;
import com.example.ShopSmartly.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTGenerator jwtGenerator;
    @Test
    public void testLoginUser_Success() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("np01cp4a210194@islingtoncollege.edu.np");
        loginDto.setPassword("nabin");

        // Create a mock UserEntity object
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setEmail(loginDto.getEmail());
        userEntity.setPassword(loginDto.getPassword());

        // Create a mock Authentication object with UserEntity as principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userEntity, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtGenerator.generateToken(userEntity)).thenReturn("mocked-token");

        mockMvc.perform(MockMvcRequestBuilders.post("/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"np01cp4a210194@islingtoncollege.edu.np\",\"password\":\"nabin\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mocked-token"));
    }
}

