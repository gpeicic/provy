package com.example.provy.ProviderOffering.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProviderOfferingRequestDTO implements ProviderOfferingDTO{
    @NotNull
    private Long providerProfileId;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer durationInMinutes;

    public ProviderOfferingRequestDTO(){}

    public ProviderOfferingRequestDTO(@NotNull Long providerProfileId, @NotNull String name, @NotNull String description, @NotNull BigDecimal price, @NotNull Integer durationInMinutes) {
        this.providerProfileId = providerProfileId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public BigDecimal getPrice() {
        return null;
    }

    @Override
    public Long getProviderProfileId() {
        return null;
    }

    @Override
    public Integer getDurationInMinutes() {
        return null;
    }
}
