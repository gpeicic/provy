package com.example.provy.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private Long userId;
    private Long providerOfferingId;
    private Long providerProfileId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private AppointmentStatus appointmentStatus;
    public Appointment(){}
    public Appointment(Long id, Long userId, Long providerProfileId, Long providerOfferingId, LocalTime startTime, LocalTime endTime, LocalDate date, AppointmentStatus status) {
        this.id = id;
        this.userId = userId;
        this.providerOfferingId = providerOfferingId;
        this.providerProfileId = providerProfileId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.appointmentStatus = status;
    }

    public Long getProviderProfileId() {
        return providerProfileId;
    }

    public void setProviderProfileId(Long providerProfileId) {
        this.providerProfileId = providerProfileId;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
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

    public Long getProviderOfferingId() {
        return providerOfferingId;
    }

    public void setProviderOfferingId(Long providerOfferingId) {
        this.providerOfferingId = providerOfferingId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
