package com.example.provy.ProviderOffering.DTO;

import java.math.BigDecimal;

public interface ProviderOfferingDTO {
    String getName();
    String getDescription();
    BigDecimal getPrice();
    Long getProviderProfileId();
    Integer getDurationInMinutes();
}
