package com.example.provy.appointment.DTO;

import com.example.provy.appointment.Appointment;
import com.example.provy.appointment.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class AppointmentDTOMapper {
    public Appointment toEntity(AppointmentRequestDTO dto, Long providerProfileId, LocalTime endTime,Long userId){
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setProviderOfferingId(dto.getProviderOfferingId());
        appointment.setProviderProfileId(providerProfileId);
        appointment.setDate(dto.getDate());
        appointment.setStartTime(dto.getStartTime());

        appointment.setEndTime(endTime);
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        return appointment;
    }


    public AppointmentResponseDTO toResponseDTO(Appointment appointment){
        AppointmentResponseDTO dto = new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getProviderOfferingId(),
                appointment.getProviderProfileId(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getAppointmentStatus()
        );
        return dto;
    }


}
