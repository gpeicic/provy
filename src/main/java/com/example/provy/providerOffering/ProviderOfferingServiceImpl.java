package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;
import com.example.provy.providerOffering.exception.ProviderOfferingAlreadyExistsException;
import com.example.provy.providerOffering.exception.ProviderOfferingNotFoundException;
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
       int deleted = providerOfferingMapper.deleteProviderOffering(id);
       if(deleted == 0){
           throw new ProviderOfferingNotFoundException();
       }
    }
}
