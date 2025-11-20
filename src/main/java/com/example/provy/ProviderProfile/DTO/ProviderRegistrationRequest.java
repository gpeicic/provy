package com.example.provy.ProviderProfile.DTO;

import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHour;
import com.example.provy.User.DTO.UserRequestDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProviderRegistrationRequest {
    @NotNull
    private UserRequestDTO user;
    @NotNull
    private ProviderProfileRequestDTO providerProfile;
    @NotNull
    private List<ProviderWorkingHour> workingHours;

    public List<ProviderWorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<ProviderWorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    public UserRequestDTO getUser() {
        return user;
    }

    public void setUser(UserRequestDTO user) {
        this.user = user;
    }

    public ProviderProfileRequestDTO getProviderProfile() {
        return providerProfile;
    }

    public void setProviderProfile(ProviderProfileRequestDTO providerProfile) {
        this.providerProfile = providerProfile;
    }
}
