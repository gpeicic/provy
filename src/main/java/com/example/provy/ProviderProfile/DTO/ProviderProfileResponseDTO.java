package com.example.provy.ProviderProfile.DTO;

import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHour;
import com.example.provy.User.DTO.UserResponseDTO;

import java.util.List;

public class ProviderProfileResponseDTO {
    private UserResponseDTO user;
    private ProviderProfileRequestDTO providerProfile;
    private List<ProviderWorkingHour> workingHours;
    public ProviderProfileResponseDTO(){}

    public ProviderProfileResponseDTO(UserResponseDTO user, ProviderProfileRequestDTO providerProfile, List<ProviderWorkingHour> workingHours) {
        this.user = user;
        this.providerProfile = providerProfile;
        this.workingHours = workingHours;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public ProviderProfileRequestDTO getProviderProfile() {
        return providerProfile;
    }

    public void setProviderProfile(ProviderProfileRequestDTO providerProfile) {
        this.providerProfile = providerProfile;
    }

    public List<ProviderWorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<ProviderWorkingHour> workingHours) {
        this.workingHours = workingHours;
    }
}
