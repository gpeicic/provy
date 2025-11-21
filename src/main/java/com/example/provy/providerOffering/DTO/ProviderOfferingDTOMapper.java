package com.example.provy.providerOffering.DTO;

import com.example.provy.providerOffering.ProviderOffering;
import org.springframework.stereotype.Component;

@Component
public class ProviderOfferingDTOMapper {
    public ProviderOffering toEntity(ProviderOfferingDTO dto){
        ProviderOffering offering = new ProviderOffering();
        offering.setName(dto.getName());
        offering.setDescription(dto.getDescription());
        offering.setPrice(dto.getPrice());
        offering.setProviderProfileId(dto.getProviderProfileId());
        offering.setDurationInMinutes(dto.getDurationInMinutes());

        return offering;
    }

    public ProviderOfferingRequestDTO toRequestDTO(ProviderOffering offering){
        ProviderOfferingRequestDTO dto = new ProviderOfferingRequestDTO(
                offering.getProviderProfileId(),
                offering.getName(),
                offering.getDescription(),
                offering.getPrice(),
                offering.getDurationInMinutes()
        );
      return dto;
    }

    public ProviderOfferingResponseDTO toResponseDTO(ProviderOffering offering){
        return new ProviderOfferingResponseDTO(
                offering.getId(),
                offering.getProviderProfileId(),
                offering.getName(),
                offering.getDescription(),
                offering.getPrice(),
                offering.getDurationInMinutes()
        );
    }
}
