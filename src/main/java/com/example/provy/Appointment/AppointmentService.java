package com.example.provy.Appointment;

import com.example.provy.Appointment.DTO.AppointmentRequestDTO;
import com.example.provy.Appointment.DTO.AppointmentResponseDTO;

public interface AppointmentService {
    AppointmentResponseDTO getById(Long id);
    void bookAppointment(AppointmentRequestDTO appointment);
    Boolean isAppointmentAvailable(Appointment appointment);
    void deleteAppointmentById(Long id);
}
