package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;

public interface ProviderProfileService {
    ProviderProfileResponseDTO getByProviderId(Long id);
    ProviderProfile registerProviderProfile(ProviderRegistrationRequest request);
    void deleteProviderProfileById(Long id);
}
