package com.example.provy.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long expirationMillis;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expirationMillis}") long expirationMillis){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(UserDetails userDetails, Long userId){
        long now = System.currentTimeMillis();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", userId)
                .claim("roles", roles)
                .issuedAt(new Date(now))
                .expiration(new Date((now + expirationMillis)))
                .signWith(key)
                .compact();

    }

    public io.jsonwebtoken.Claims validateAndGetClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
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
