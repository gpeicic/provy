package com.example.provy.auth;

import com.example.provy.role.RoleMapper;
import com.example.provy.security.CustomUserDetailsService;
import com.example.provy.security.JwtUtil;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, UserMapper userMapper, RoleMapper roleMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    public String authenticate(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Long userId = userMapper.getUserByEmail(email).getId();

        return jwtUtil.generateToken(userDetails, userId);
    }

    public String generateTokenForUser(User user){

        List<String> roles = roleMapper.getRolesByUserId(user.getId());

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
        return jwtUtil.generateToken(userDetails, user.getId());
    }
}
