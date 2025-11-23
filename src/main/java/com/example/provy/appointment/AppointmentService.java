package com.example.provy.appointment;

import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDTO getById(Long id);
    List<AppointmentResponseDTO> getAllByProvider(Long providerId);
    void bookAppointment(AppointmentRequestDTO appointment);
    void deleteAppointmentById(Long id);
}
