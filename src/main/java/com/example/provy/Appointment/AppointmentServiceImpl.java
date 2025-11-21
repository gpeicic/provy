package com.example.provy.Appointment;

import com.example.provy.Appointment.DTO.AppointmentDTOMapper;
import com.example.provy.Appointment.DTO.AppointmentRequestDTO;
import com.example.provy.Appointment.DTO.AppointmentResponseDTO;
import com.example.provy.Appointment.Exception.AppointmentNotFoundException;
import com.example.provy.Appointment.Exception.InvalidAppointmentTimeException;
import com.example.provy.Common.Validation.ValidPassword;
import com.example.provy.ProviderOffering.ProviderOffering;
import com.example.provy.ProviderOffering.ProviderOfferingMapper;
import com.example.provy.ProviderProfile.Exception.ProviderNotFoundException;
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
        Appointment appointment = appointmentMapper.getById(id);
        if(appointment == null){
            throw new AppointmentNotFoundException(id);
        }
        return appointmentDTOMapper.toResponseDTO(appointment);
    }
    @Override
    public void bookAppointment(AppointmentRequestDTO appointmentRequestDTO){
        ProviderOffering providerOffering = providerOfferingMapper.getById(appointmentRequestDTO.getProviderOfferingId());

        if(providerOffering == null){
            throw new ProviderNotFoundException(appointmentRequestDTO.getProviderOfferingId());
        }
        if(appointmentRequestDTO.getStartTime() == null){
            throw new InvalidAppointmentTimeException("Appointment start time must be set");
        }

        LocalTime endTime = appointmentRequestDTO.getStartTime().plusMinutes(providerOffering.getDurationInMinutes());

        Appointment appointment = appointmentDTOMapper.toEntity(
                appointmentRequestDTO,
                providerOffering.getProviderProfileId(),
                endTime
        );

        if(!isAppointmentAvailable(appointment)){
            throw new InvalidAppointmentTimeException("The appointment slot from " + appointment.getStartTime()
                    + " to " + appointment.getEndTime() + " is already taken");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentMapper.bookAppointment(appointment);
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
       int deleted = appointmentMapper.deleteAppointmentById(id);
       if(deleted == 0){
           throw new AppointmentNotFoundException(id);
       }
    }
}
