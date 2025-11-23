package com.example.provy.appointment;

import com.example.provy.appointment.DTO.AppointmentDTOMapper;
import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;
import com.example.provy.appointment.exception.AppointmentNotFoundException;
import com.example.provy.appointment.exception.InvalidAppointmentTimeException;
import com.example.provy.providerOffering.ProviderOffering;
import com.example.provy.providerOffering.ProviderOfferingMapper;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.security.AuthorizationService;
import com.example.provy.security.CustomUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentMapper appointmentMapper;
    private final AppointmentDTOMapper appointmentDTOMapper;
    private final ProviderOfferingMapper providerOfferingMapper;
    private final AppointmentValidator appointmentValidator;
    private static final String APPOINTMENT_DELETE_ERROR ="You are not allowed to delete this appointment.";


    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, AppointmentDTOMapper appointmentDTOMapper,
                                  ProviderOfferingMapper providerOfferingMapper, AppointmentValidator appointmentValidator) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentDTOMapper = appointmentDTOMapper;
        this.providerOfferingMapper = providerOfferingMapper;
        this.appointmentValidator = appointmentValidator;
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
    public  List<AppointmentResponseDTO> getAllByProvider(Long providerId){
        List<AppointmentResponseDTO> appointments = appointmentMapper.getAppointmentsByProvider(providerId).stream()
                .map(appointmentDTOMapper :: toResponseDTO)
                .collect(Collectors.toList());
        return  appointments;
    }

    @Override
    public void bookAppointment(AppointmentRequestDTO appointmentRequestDTO){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long userId = currentUser.getId();

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
                endTime,
                userId
        );

        appointmentValidator.isAppointmentAvailable(appointment);

        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentMapper.bookAppointment(appointment);
    }

    @Override
    public void deleteAppointmentById(Long id){
        Appointment appointment = appointmentMapper.getById(id);

        AuthorizationService.authorizeCurrentUserOrAdmin(appointment.getUserId(),APPOINTMENT_DELETE_ERROR);

        int deleted = appointmentMapper.deleteAppointmentById(id);
        if(deleted == 0){
           throw new AppointmentNotFoundException(id);
        }
    }

}
