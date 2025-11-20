package com.example.provy.Appointment.DTO;

import com.example.provy.Appointment.Appointment;
import com.example.provy.Appointment.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDTOMapper {
    public Appointment toEntity(AppointmentRequestDTO dto, Long providerProfileId, Integer durationInMinutes){
        Appointment appointment = new Appointment();
        appointment.setUserId(dto.getUserId());
        appointment.setProviderOfferingId(dto.getProviderOfferingId());
        appointment.setProviderProfileId(providerProfileId);
        appointment.setDate(dto.getDate());
        appointment.setStartTime(dto.getStartTime());

        appointment.setEndTime(dto.getStartTime().plusMinutes(durationInMinutes));
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        return appointment;
    }

    public Appointment toEntity(AppointmentResponseDTO dto){
        Appointment appointment = new Appointment(
                dto.getId(),
                dto.getUserId(),
                dto.getProviderProfileId(),
                dto.getProviderOfferingId(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getDate(),
                dto.getAppointmentStatus()
        );
        return appointment;
    }

    public AppointmentResponseDTO toResponseDTO(Appointment appointment){
        AppointmentResponseDTO dto = new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getUserId(),
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
