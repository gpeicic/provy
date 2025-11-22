package com.example.provy.appointment;

import com.example.provy.appointment.DTO.AppointmentDTOMapper;
import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;
import com.example.provy.appointment.exception.AppointmentNotFoundException;
import com.example.provy.appointment.exception.InvalidAppointmentTimeException;
import com.example.provy.providerOffering.ProviderOffering;
import com.example.provy.providerOffering.ProviderOfferingMapper;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.security.CustomUserDetails;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserMapper userMapper;


    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, AppointmentDTOMapper appointmentDTOMapper, ProviderOfferingMapper providerOfferingMapper, UserMapper userMapper) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentDTOMapper = appointmentDTOMapper;
        this.providerOfferingMapper = providerOfferingMapper;
        this.userMapper = userMapper;
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
        User user = userMapper.getUserById(id);

        authorizeCurrentUserOrAdmin(user);

        int deleted = appointmentMapper.deleteAppointmentById(id);
        if(deleted == 0){
           throw new AppointmentNotFoundException(id);
        }
    }

    public static void authorizeCurrentUserOrAdmin(User user) {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long currentUserId = currentUser.getId();

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin && !user.getId().equals(currentUserId)){
            throw new AccessDeniedException("You are not allowed to delete this user profile.");
        }
    }
}
