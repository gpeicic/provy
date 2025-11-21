package com.example.provy.user;

import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO getUserById(Long id);
    void registerUser(UserRequestDTO user);
    void deleteUser(Long id);
}
