package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;
import com.example.provy.providerOffering.exception.ProviderOfferingAlreadyExistsException;
import com.example.provy.providerOffering.exception.ProviderOfferingNotFoundException;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.ProviderProfile;
import com.example.provy.providerProfile.ProviderProfileService;
import com.example.provy.security.CustomUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Transactional
@Service
public class ProviderOfferingServiceImpl implements ProviderOfferingService {
    private final ProviderOfferingMapper providerOfferingMapper;
    private final ProviderProfileService providerProfileService;
    private final ProviderOfferingDTOMapper providerOfferingDTOMapper;

    public ProviderOfferingServiceImpl(ProviderOfferingMapper providerOfferingMapper,
                                       ProviderProfileService providerProfileService,
                                       ProviderOfferingDTOMapper providerOfferingDTOMapper) {
        this.providerOfferingMapper = providerOfferingMapper;
        this.providerProfileService = providerProfileService;
        this.providerOfferingDTOMapper = providerOfferingDTOMapper;
    }

    @Override
    public ProviderOfferingResponseDTO getById(Long id){
        ProviderOffering offering = providerOfferingMapper.getById(id);
        if(offering == null){
            throw new ProviderOfferingNotFoundException();
        }
        ProviderOfferingResponseDTO providerOfferingResponse =providerOfferingDTOMapper.toResponseDTO(providerOfferingMapper.getById(id));
        return providerOfferingResponse;
    }
    @Override
    public ProviderOfferingResponseDTO getByProviderProfileId(Long id){
        ProviderOfferingResponseDTO providerOfferingResponse = providerOfferingDTOMapper.toResponseDTO(providerOfferingMapper.getByProviderProfileId(id));
        if(providerOfferingResponse == null){
            throw new ProviderOfferingNotFoundException();
        }

        return providerOfferingResponse;
    }
    @Override
    public void registerProviderOffering(ProviderOfferingRequestDTO providerOfferingRequest){
        if(providerOfferingMapper.getCountByProviderIdAndName(providerOfferingRequest.getProviderProfileId(), providerOfferingRequest.getName()) == 0){
            throw new ProviderOfferingAlreadyExistsException(providerOfferingRequest.getName());
        }
        providerOfferingMapper.registerProviderOffering(providerOfferingDTOMapper.toEntity(providerOfferingRequest));
    }
    @Override
    public void deleteProviderOffering(Long id){
        ProviderOffering offering = providerOfferingMapper.getById(id);
        ProviderProfileResponseDTO profile = providerProfileService.getByProviderId(offering.getProviderProfileId());

        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long currentUserId = currentUser.getId();

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin && !profile.getProviderProfile().getUser_id().equals(currentUserId)){
            throw new AccessDeniedException("You are not allowed to delete this provider offering.");
        }


       int deleted = providerOfferingMapper.deleteProviderOffering(id);
       if(deleted == 0){
           throw new ProviderOfferingNotFoundException();
       }
    }
}
