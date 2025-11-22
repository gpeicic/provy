package com.example.provy.auth;

import com.example.provy.infrastructure.security.CustomUserDetailsService;
import com.example.provy.infrastructure.security.JwtUtil;
import com.example.provy.user.UserMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       CustomUserDetailsService userDetailsService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    public String authenticate(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Long userId = userMapper.getByEmail(email).getId();

        return jwtUtil.generateToken(userDetails, userId);
    }
}
