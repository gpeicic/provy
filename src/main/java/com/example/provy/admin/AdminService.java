package com.example.provy.admin;

import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.User;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    List<ProviderProfileResponseDTO> getAllProviderProfiles();
    User createAdmin(UserRequestDTO dto);
    ProviderProfileResponseDTO getProviderById(Long id);
    void deleteUser(Long id);
    void deleteProvider(Long id);
}
