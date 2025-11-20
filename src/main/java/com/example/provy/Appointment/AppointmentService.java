package com.example.provy.Appointment;

public interface AppointmentService {
    Appointment getById(Long id);
    void bookAppointment(Appointment appointment);
    Boolean isAppointmentAvailable(Appointment appointment);
    void deleteAppointmentById(Long id);
}
