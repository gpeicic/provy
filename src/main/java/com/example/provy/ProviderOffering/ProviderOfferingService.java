package com.example.provy.ProviderOffering;

import com.example.provy.ProviderOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.ProviderOffering.DTO.ProviderOfferingResponseDTO;

public interface ProviderOfferingService {

    ProviderOfferingResponseDTO getById(Long id);
    ProviderOfferingResponseDTO getByProviderProfileId(Long id);
    void registerProviderOffering(ProviderOfferingRequestDTO providerOffering);
    void deleteProviderOffering(Long id);
}
