package com.example.provy.ProviderOffering;

import com.example.provy.ProviderOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.ProviderOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.ProviderOffering.DTO.ProviderOfferingResponseDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Transactional
@Service
public class ProviderOfferingServiceImpl implements ProviderOfferingService {
    private final ProviderOfferingMapper providerOfferingMapper;
    private final ProviderOfferingDTOMapper providerOfferingDTOMapper;

    public ProviderOfferingServiceImpl(ProviderOfferingMapper providerOfferingMapper,ProviderOfferingDTOMapper providerOfferingDTOMapper){
        this.providerOfferingMapper = providerOfferingMapper;
        this.providerOfferingDTOMapper = providerOfferingDTOMapper;
    }
    @Override
    public ProviderOfferingResponseDTO getById(Long id){
        ProviderOffering offering = providerOfferingMapper.getById(id);
        System.out.println(offering.getDescription() + " " + offering.getName());
        ProviderOfferingResponseDTO providerOfferingResponse =providerOfferingDTOMapper.toResponseDTO(providerOfferingMapper.getById(id));

        return providerOfferingResponse;
    }
    @Override
    public ProviderOfferingResponseDTO getByProviderProfileId(Long id){
        ProviderOfferingResponseDTO providerOfferingResponse = providerOfferingDTOMapper.toResponseDTO(providerOfferingMapper.getByProviderProfileId(id));

        return providerOfferingResponse;
    }
    @Override
    public void registerProviderOffering(ProviderOfferingRequestDTO providerOfferingRequest){
        providerOfferingMapper.registerProviderOffering(providerOfferingDTOMapper.toEntity(providerOfferingRequest));
    }
    @Override
    public void deleteProviderOffering(Long id){
        providerOfferingMapper.deleteProviderOffering(id);
    }
}
