package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;

public interface ProviderOfferingService {

    ProviderOfferingResponseDTO getById(Long id);
    ProviderOfferingResponseDTO getByProviderProfileId(Long id);
    void registerProviderOffering(ProviderOfferingRequestDTO providerOffering);
    void deleteProviderOffering(Long id);
}
