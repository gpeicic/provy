package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;
import com.example.provy.providerOffering.exception.ProviderOfferingAlreadyExistsException;
import com.example.provy.providerOffering.exception.ProviderOfferingNotFoundException;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.ProviderProfile;
import com.example.provy.providerProfile.ProviderProfileService;
import com.example.provy.security.AuthorizationService;
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
    private final ProviderOfferingFactory providerOfferingFactory;
    private final static String PROVIDER_PROFILE_OFFERING_DELETE_ERROR = "You do not have permission to delete this provider offering.";
    public ProviderOfferingServiceImpl(ProviderOfferingMapper providerOfferingMapper,
                                       ProviderProfileService providerProfileService,
                                       ProviderOfferingDTOMapper providerOfferingDTOMapper,
                                       ProviderOfferingFactory providerOfferingFactory) {
        this.providerOfferingMapper = providerOfferingMapper;
        this.providerProfileService = providerProfileService;
        this.providerOfferingDTOMapper = providerOfferingDTOMapper;
        this.providerOfferingFactory = providerOfferingFactory;
    }

    @Override
    public ProviderOfferingResponseDTO getById(Long id){
        ProviderOffering offering = providerOfferingMapper.getById(id);
        if(offering == null){
            throw new ProviderOfferingNotFoundException();
        }
        return providerOfferingDTOMapper.toResponseDTO(providerOfferingMapper.getById(id));
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
        ProviderOffering offering = providerOfferingFactory.create(providerOfferingRequest);
        providerOfferingMapper.registerProviderOffering(offering);
    }
    @Override
    public void deleteProviderOffering(Long id){
        ProviderOffering offering = providerOfferingMapper.getById(id);
        ProviderProfileResponseDTO profile = providerProfileService.getByProviderId(offering.getProviderProfileId());
        AuthorizationService.authorizeCurrentUserOrAdmin(profile.getUser().getId(), PROVIDER_PROFILE_OFFERING_DELETE_ERROR );

       int deleted = providerOfferingMapper.deleteProviderOffering(id);
       if(deleted == 0){
           throw new ProviderOfferingNotFoundException();
       }
    }
}
