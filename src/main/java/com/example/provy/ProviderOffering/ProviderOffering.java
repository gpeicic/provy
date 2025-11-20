package com.example.provy.ProviderOffering;

import java.math.BigDecimal;

public class ProviderOffering {
    private Long id;
    private Long providerProfileId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationInMinutes;
    public ProviderOffering(){}

    public ProviderOffering(Long id, Long providerProfileId, String name, String description, BigDecimal price, Integer durationInMinutes) {
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

    public Long getProviderProfileId() {
        return providerProfileId;
    }

    public void setProviderProfileId(Long providerProfileId) {
        this.providerProfileId = providerProfileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
