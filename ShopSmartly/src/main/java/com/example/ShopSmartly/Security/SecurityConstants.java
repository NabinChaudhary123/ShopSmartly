package com.example.ShopSmartly.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 10*60*1000;

    public static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
