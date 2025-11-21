package com.example.provy.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long expirationMilis;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationMilis){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMilis = expirationMilis;
    }

    public String generateToken(Long userId, String email, List<String> roles){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date((now + expirationMilis)))
                .signWith(key)
                .compact();

    }

    public io.jsonwebtoken.Claims validateAndGetClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseEncryptedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token){
        return validateAndGetClaims(token).getSubject();
    }

    public Long getUserIdFromToken(String token){
        return ((Number) validateAndGetClaims(token).get("userId")).longValue();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token){
        return (List<String>) validateAndGetClaims(token).get("roles");
    }
}
