package com.example.provy.appointment;

import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;

public interface AppointmentService {
    AppointmentResponseDTO getById(Long id);
    void bookAppointment(AppointmentRequestDTO appointment);
    Boolean isAppointmentAvailable(Appointment appointment);
    void deleteAppointmentById(Long id);
}
