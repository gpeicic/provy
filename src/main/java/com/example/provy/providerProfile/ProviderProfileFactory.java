package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderProfileRequestDTO;
import com.example.provy.providerProfile.exception.ProviderAlreadyExists;
import org.springframework.stereotype.Component;

@Component
public class ProviderProfileFactory {
    private final ProviderProfileMapper providerProfileMapper;

    public ProviderProfileFactory(ProviderProfileMapper providerProfileMapper) {
        this.providerProfileMapper = providerProfileMapper;
    }

    public ProviderProfile createProfile(Long userId, ProviderProfileRequestDTO dto){

        if(providerProfileMapper.getCountByBusinessName(dto.getBusinessName()) > 0){
            throw new ProviderAlreadyExists(dto.getBusinessName());
        }

        ProviderProfile profile = new ProviderProfile();
        profile.setUserId(userId);
        profile.setBusinessName(dto.getBusinessName());
        profile.setAddress(dto.getAddress());
        profile.setPhone(dto.getPhone());
        profile.setDescription(dto.getDescription());
        profile.setStatus(ProviderStatus.PENDING);

        providerProfileMapper.registerProviderProfile(profile);

        return profile;
    }
}
