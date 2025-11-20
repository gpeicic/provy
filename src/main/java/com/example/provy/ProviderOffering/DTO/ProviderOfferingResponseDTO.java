package com.example.provy.ProviderOffering.DTO;

import java.math.BigDecimal;

public class ProviderOfferingResponseDTO implements ProviderOfferingDTO {
    private Long id;
    private Long providerProfileId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationInMinutes;

    public ProviderOfferingResponseDTO(){}

    public ProviderOfferingResponseDTO(Long id, Long providerProfileId, String name, String description, BigDecimal price, Integer durationInMinutes) {
        this.id = id;
        this.providerProfileId = providerProfileId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Long getProviderProfileId() {
        return providerProfileId;
    }

    @Override
    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
}
