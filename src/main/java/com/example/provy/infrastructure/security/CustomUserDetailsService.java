package com.example.provy.infrastructure.security;

import com.example.provy.role.RoleMapper;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public CustomUserDetailsService(UserMapper userMapper, RoleMapper roleMapper){
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userMapper.getByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User not found with email: " + email);

        List<GrantedAuthority> authorities = roleMapper.getRolesByUserId(user.getId())
                .stream()
                .map(SimpleGrantedAuthority :: new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
