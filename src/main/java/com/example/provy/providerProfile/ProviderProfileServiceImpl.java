package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.security.AuthorizationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Primary
@Service
@Transactional
public class ProviderProfileServiceImpl implements ProviderProfileService{
    private final ProviderRegistrationService providerRegistrationService;
    private final ProviderProfileMapper providerProfileMapper;
    private final ProviderProfileDTOMapper providerProfileDTOMapper;
    private static final String PROVIDER_PROFILE_AUTHORIZE_ERROR = "You do not have permission to access this provider.";
    private static final String PROVIDER_PROFILE_DELETE_ERROR = "You are not allowed to delete this provider profile.";

    public ProviderProfileServiceImpl(ProviderRegistrationService providerRegistrationService, ProviderProfileMapper providerProfileMapper,
                                      ProviderProfileDTOMapper providerProfileDTOMapper) {
        this.providerRegistrationService = providerRegistrationService;
        this.providerProfileMapper = providerProfileMapper;
        this.providerProfileDTOMapper = providerProfileDTOMapper;
    }

    @Override
    @Cacheable(value = "providerProfiles", key = "#id")
    public ProviderProfileResponseDTO getByProviderId(Long id){
        ProviderProfile profile = providerProfileMapper.getByProviderId(id);
        if(profile == null){
            throw new ProviderNotFoundException(id);
        }
        AuthorizationService.authorizeCurrentUserOrAdmin(profile.getUserId(), PROVIDER_PROFILE_AUTHORIZE_ERROR);
        return providerProfileDTOMapper.toResponseDTO(providerProfileMapper.getByProviderId(id));
    }
    @Override
    @CachePut(value = "providerProfiles", key = "#result.providerId")
    public ProviderProfile registerProviderProfile(ProviderRegistrationRequest request){
        return providerRegistrationService.registerProvider(request);
    }


    @Override
    @CacheEvict(value = "providerProfiles", key = "#id")
    public void deleteProviderProfileById(Long id){
        ProviderProfile profile = providerProfileMapper.getByProviderId(id);
        AuthorizationService.authorizeCurrentUserOrAdmin(profile.getUserId(), PROVIDER_PROFILE_DELETE_ERROR);

        int deleted = providerProfileMapper.deleteProviderProfileById(id);
        if (deleted == 0){
            throw new ProviderNotFoundException(id);
        }

    }

}
