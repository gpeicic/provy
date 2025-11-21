package com.example.provy.providerProfile.DTO;

import com.example.provy.providerProfile.providerWorkingHour.ProviderWorkingHour;
import com.example.provy.user.DTO.UserRequestDTO;
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
