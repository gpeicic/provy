package com.example.provy.appointment.DTO;

import com.example.provy.appointment.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponseDTO {
    private Long id;
    private Long providerOfferingId;
    private Long providerProfileId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus appointmentStatus;

    public AppointmentResponseDTO(){}

    public AppointmentResponseDTO(Long id, Long providerOfferingId, Long providerProfileId, LocalDate date,
                                  LocalTime startTime, LocalTime endTime, AppointmentStatus appointmentStatus) {
        this.id = id;
        this.providerOfferingId = providerOfferingId;
        this.providerProfileId = providerProfileId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getProviderOfferingId() {
        return providerOfferingId;
    }

    public void setProviderOfferingId(Long providerOfferingId) {
        this.providerOfferingId = providerOfferingId;
    }

    public Long getProviderProfileId() {
        return providerProfileId;
    }

    public void setProviderProfileId(Long providerProfileId) {
        this.providerProfileId = providerProfileId;
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

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
}
