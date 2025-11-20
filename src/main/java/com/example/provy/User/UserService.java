package com.example.provy.User;

import com.example.provy.User.DTO.UserRequestDTO;
import com.example.provy.User.DTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO getUserById(Long id);
    void registerUser(UserRequestDTO user);
    void deleteUser(Long id);
}
