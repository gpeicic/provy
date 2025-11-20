package com.example.provy.Appointment;

import com.example.provy.Appointment.DTO.AppointmentDTOMapper;
import com.example.provy.Appointment.DTO.AppointmentRequestDTO;
import com.example.provy.Appointment.DTO.AppointmentResponseDTO;
import com.example.provy.ProviderOffering.ProviderOffering;
import com.example.provy.ProviderOffering.ProviderOfferingMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Primary
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentMapper appointmentMapper;
    private final AppointmentDTOMapper appointmentDTOMapper;
    private final ProviderOfferingMapper providerOfferingMapper;


    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, ProviderOfferingMapper providerOfferingMapper,AppointmentDTOMapper appointmentDTOMapper){
        this.appointmentMapper = appointmentMapper;
        this.providerOfferingMapper = providerOfferingMapper;
        this.appointmentDTOMapper = appointmentDTOMapper;
    }
    @Override
    public AppointmentResponseDTO getById(Long id){
        return appointmentDTOMapper.toResponseDTO(appointmentMapper.getById(id));
    }
    @Override
    public void bookAppointment(AppointmentRequestDTO appointmentRequestDTO){
        ProviderOffering providerOffering = providerOfferingMapper.getById(appointmentRequestDTO.getProviderOfferingId());
        LocalTime endTime = appointmentRequestDTO.getStartTime().plusMinutes(providerOffering.getDurationInMinutes());

        Appointment appointment = appointmentDTOMapper.toEntity(
                appointmentRequestDTO,
                providerOffering.getProviderProfileId(),
                endTime
        );

        AppointmentStatus status = isAppointmentAvailable(appointment)
                ? AppointmentStatus.CONFIRMED
                : AppointmentStatus.REJECTED;

        appointment.setAppointmentStatus(status);

    }
    @Override
    public Boolean isAppointmentAvailable(Appointment appointment){
        List<Appointment> appointments = appointmentMapper.getAppointmentsByProvider(appointment.getProviderProfileId());
        for(Appointment a : appointments){
           boolean sameDate = a.getDate().isEqual(appointment.getDate());
           boolean confirmed = a.getAppointmentStatus() == AppointmentStatus.CONFIRMED;
           boolean overlaps = !appointment.getEndTime().isBefore(a.getStartTime()) &&
                   !appointment.getStartTime().isAfter(a.getEndTime());

           if(sameDate && confirmed && overlaps){
               return Boolean.FALSE;
           }
        }

        return Boolean.TRUE;
    }
    @Override
    public void deleteAppointmentById(Long id){
        appointmentMapper.deleteAppointmentById(id);
    }
}
