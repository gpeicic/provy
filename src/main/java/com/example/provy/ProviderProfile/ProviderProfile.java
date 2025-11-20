package com.example.provy.ProviderProfile;

public class ProviderProfile {

    private Long id;
    private Long user_id;
    private String businessName;
    private String address;
    private String phone;
    private String description;
    private ProviderStatus status = ProviderStatus.PENDING;

    public ProviderProfile(Long id, Long userId, String businessName, String address, String phone, String description) {
        this.id = id;
        this.user_id = userId;
        this.businessName = businessName;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }

    public ProviderProfile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProviderStatus getStatus() {
        return status;
    }

    public void setStatus(ProviderStatus status) {
        this.status = status;
    }
}
