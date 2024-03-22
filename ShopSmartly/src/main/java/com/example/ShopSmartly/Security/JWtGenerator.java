package com.example.ShopSmartly.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.ShopSmartly.Security.SecurityConstants.*;

@Component
public class JWtGenerator {

    public String generateToken(Authentication authentication){
        String email =authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime()+JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .signWith(secretKey)
                .compact();
        return token;
    }

    public String getEmailFromJWT(String token){
        Claims claims =Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){

        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }
        catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired");
        }
    }
}
