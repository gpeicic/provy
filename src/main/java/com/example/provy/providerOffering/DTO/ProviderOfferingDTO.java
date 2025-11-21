package com.example.provy.providerOffering.DTO;

import java.math.BigDecimal;

public interface ProviderOfferingDTO {
    String getName();
    String getDescription();
    BigDecimal getPrice();
    Long getProviderProfileId();
    Integer getDurationInMinutes();
}
