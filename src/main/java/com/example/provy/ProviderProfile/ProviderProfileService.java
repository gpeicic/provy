package com.example.provy.ProviderProfile;

import com.example.provy.ProviderProfile.DTO.ProviderProfileRequestDTO;
import com.example.provy.ProviderProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.ProviderProfile.DTO.ProviderRegistrationRequest;

public interface ProviderProfileService {
    ProviderProfileResponseDTO getByProviderId(Long id);
    ProviderProfile registerProviderProfile(ProviderRegistrationRequest request);
    void deleteProviderProfileById(Long id);
}
