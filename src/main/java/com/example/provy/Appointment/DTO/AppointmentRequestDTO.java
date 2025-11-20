package com.example.provy.Appointment.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long providerOfferingId;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;

    public AppointmentRequestDTO() {
    }

    public AppointmentRequestDTO(@NotNull Long userId, @NotNull Long providerOfferingId, @NotNull LocalDate date, @NotNull LocalTime startTime) {
        this.userId = userId;
        this.providerOfferingId = providerOfferingId;
        this.date = date;
        this.startTime = startTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProviderOfferingId() {
        return providerOfferingId;
    }

    public void setProviderOfferingId(Long providerOfferingId) {
        this.providerOfferingId = providerOfferingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
