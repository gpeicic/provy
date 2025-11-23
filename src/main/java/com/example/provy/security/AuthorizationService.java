package com.example.provy.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    public static void authorizeCurrentUserOrAdmin(Long userId, String message) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long currentUserId = currentUser.getId();

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin && !userId.equals(currentUserId)){
            throw new AccessDeniedException(message);
        }
    }
}
