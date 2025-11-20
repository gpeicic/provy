package com.example.provy.ProviderProfile.DTO;

import com.example.provy.ProviderProfile.ProviderStatus;
import jakarta.validation.constraints.NotNull;

public class ProviderProfileRequestDTO {
    @NotNull
    private Long user_id;
    @NotNull
    private String businessName;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @NotNull
    private String description;
    @NotNull
    private ProviderStatus status;
    public ProviderProfileRequestDTO(){}

    public ProviderProfileRequestDTO(Long user_id, String businessName, String address, String phone, String description, ProviderStatus providerStatus) {
        this.user_id = user_id;
        this.businessName = businessName;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.status = providerStatus;
    }

    public ProviderStatus getStatus() {
        return status;
    }

    public void setStatus(ProviderStatus status) {
        this.status = status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
