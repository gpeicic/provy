package com.example.provy.user;

import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO getUserById(Long id);
    User registerUser(UserRequestDTO user);

    User getUserByEmail(String email);
    void deleteUser(Long id);
    void insertRole(Long userId, Long roleId);
}
