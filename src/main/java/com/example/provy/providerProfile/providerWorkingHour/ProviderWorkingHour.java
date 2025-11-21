package com.example.provy.providerProfile.providerWorkingHour;

import java.time.LocalTime;

public class ProviderWorkingHour {
    private Long id;
    private Long providerProfileId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public ProviderWorkingHour() {
    }

    public ProviderWorkingHour(Long id, Long providerProfileId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.providerProfileId = providerProfileId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
}
