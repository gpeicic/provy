package com.example.provy.notification;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private Long userId;
    private Long appointmentId;
    private String message;
    private LocalDateTime scheduledTime;
    private boolean sent;

    public Notification(){}
    public Notification(Long id, Long userId, Long appointmentId, String message, LocalDateTime scheduledTime, boolean sent) {
        this.id = id;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.message = message;
        this.scheduledTime = scheduledTime;
        this.sent = sent;
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

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
