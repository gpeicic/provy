package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.exception.ProviderOfferingAlreadyExistsException;
import org.springframework.stereotype.Component;


@Component
public class ProviderOfferingFactory {

    private final ProviderOfferingMapper providerOfferingMapper;
    private final ProviderOfferingDTOMapper providerOfferingDTOMapper;

    public ProviderOfferingFactory(ProviderOfferingMapper providerOfferingMapper, ProviderOfferingDTOMapper providerOfferingDTOMapper) {
        this.providerOfferingMapper = providerOfferingMapper;
        this.providerOfferingDTOMapper = providerOfferingDTOMapper;
    }

    public ProviderOffering create(ProviderOfferingRequestDTO dto){
         if(providerOfferingMapper.getCountByProviderIdAndName(dto.getProviderProfileId(), dto.getName()) == 0){
             throw new ProviderOfferingAlreadyExistsException(dto.getName());
         }
         return  providerOfferingDTOMapper.toEntity(dto);
     }
}
