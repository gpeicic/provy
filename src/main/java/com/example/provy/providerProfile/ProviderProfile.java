package com.example.provy.providerProfile;

public class ProviderProfile {
    private Long id;
    private Long userId;
    private String businessName;
    private String address;
    private String phone;
    private String description;
    private Double latitude;
    private Double longitude;
    private ProviderStatus status = ProviderStatus.PENDING;

    public ProviderProfile(Long id, Long userId, String businessName, String address, String phone, String description, Double latitude, Double longitude, ProviderStatus status) {
        this.id = id;
        this.userId = userId;
        this.businessName = businessName;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public ProviderProfile() {

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
