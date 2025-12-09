package com.example.provy.appointment;
import com.example.provy.appointment.DTO.AppointmentDTOMapper;
import com.example.provy.appointment.DTO.AppointmentRequestDTO;
import com.example.provy.appointment.DTO.AppointmentResponseDTO;
import com.example.provy.appointment.exception.AppointmentNotFoundException;
import com.example.provy.appointment.exception.InvalidAppointmentTimeException;
import com.example.provy.notification.NotificationService;
import com.example.provy.providerOffering.ProviderOffering;
import com.example.provy.providerOffering.ProviderOfferingMapper;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private AppointmentDTOMapper appointmentDTOMapper;
    @Mock
    private ProviderOfferingMapper providerOfferingMapper;
    @Mock
    private AppointmentValidator appointmentValidator;
    @Mock
    private NotificationService notificationService;

    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void setup() {
        appointmentService = new AppointmentServiceImpl(
                appointmentMapper,
                appointmentDTOMapper,
                providerOfferingMapper,
                appointmentValidator,
                notificationService
        );

        SecurityContextHolder.clearContext();
    }

    // GET APPOINTMENT BY ID - GOOD PATH
    @Test
    void getById_ReturnsDTO_returnsResponseDTO() {
        Long id = 1L;
        Appointment appointment = new Appointment();
        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        when(appointmentMapper.getById(id)).thenReturn(appointment);
        when(appointmentDTOMapper.toResponseDTO(appointment)).thenReturn(dto);

        AppointmentResponseDTO result = appointmentService.getById(id);

        assertEquals(dto, result);
    }

    // GET APPOINTMENT BY ID - THROWS EXCEPTION
    @Test
    void getById_ThrowsException_WhenNotFound() {
        when(appointmentMapper.getById(1L)).thenReturn(null);

        assertThrows(AppointmentNotFoundException.class, () ->
                appointmentService.getById(1L));
    }

    //GET ALL BY PROVIDER - GOOD PATH
    @Test
    void getAllByProvider_ReturnsMappedDTOs() {
        Long providerId = 10L;

        Appointment a1 = new Appointment();
        Appointment a2 = new Appointment();
        AppointmentResponseDTO d1 = new AppointmentResponseDTO();
        AppointmentResponseDTO d2 = new AppointmentResponseDTO();

        when(appointmentMapper.getAppointmentsByProvider(providerId))
                .thenReturn(List.of(a1, a2));

        when(appointmentDTOMapper.toResponseDTO(a1)).thenReturn(d1);
        when(appointmentDTOMapper.toResponseDTO(a2)).thenReturn(d2);

        List<AppointmentResponseDTO> result = appointmentService.getAllByProvider(providerId);

        assertEquals(2, result.size());
        assertTrue(result.contains(d1));
        assertTrue(result.contains(d2));
    }

    // BOOK APPOINTMENT - GOOD PATH
    private void mockSecurityUser(Long id) {
        CustomUserDetails user = mock(CustomUserDetails.class);
        when(user.getId()).thenReturn(id);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void bookAppointment_SuccessfullyBooksAndCreatesNotifications() {
        mockSecurityUser(7L); // logged in user id = 7

        AppointmentRequestDTO req = new AppointmentRequestDTO();
        req.setProviderOfferingId(5L);
        req.setStartTime(LocalTime.of(10, 0));

        ProviderOffering offering = new ProviderOffering();
        offering.setProviderProfileId(3L);
        offering.setDurationInMinutes(60);

        Appointment mappedAppointment = new Appointment();
        mappedAppointment.setUserId(7L);
        mappedAppointment.setDate(LocalDate.now());
        mappedAppointment.setStartTime(req.getStartTime());

        when(providerOfferingMapper.getById(5L)).thenReturn(offering);

        when(appointmentDTOMapper.toEntity(
                eq(req),
                eq(3L),
                eq(LocalTime.of(11, 0)),
                eq(7L)
        )).thenReturn(mappedAppointment);

        appointmentService.bookAppointment(req);

        verify(appointmentValidator).isAppointmentAvailable(mappedAppointment);
        verify(appointmentMapper).bookAppointment(mappedAppointment);
        verify(notificationService).createNotificationsForAppointment(mappedAppointment);

        assertEquals(AppointmentStatus.CONFIRMED, mappedAppointment.getAppointmentStatus());
    }
    // BOOK APPOINTMENT - THROWS NOT FOUND EXCEPTION
    @Test
    void bookAppointment_Throws_WhenProviderOfferingNotFound() {
        mockSecurityUser(7L);

        AppointmentRequestDTO req = new AppointmentRequestDTO();
        req.setProviderOfferingId(999L);
        req.setStartTime(LocalTime.of(10, 0));

        when(providerOfferingMapper.getById(999L)).thenReturn(null);

        assertThrows(ProviderNotFoundException.class, () ->
                appointmentService.bookAppointment(req));
    }

    // BOOK APPOINTMENT - THROWS INVALID TIME
    @Test
    void bookAppointment_Throws_WhenStartTimeIsNull() {
        mockSecurityUser(7L);

        AppointmentRequestDTO req = new AppointmentRequestDTO();
        req.setProviderOfferingId(5L);
        req.setStartTime(null);

        ProviderOffering offering = new ProviderOffering();
        when(providerOfferingMapper.getById(5L)).thenReturn(offering);

        assertThrows(InvalidAppointmentTimeException.class, () ->
                appointmentService.bookAppointment(req));
    }

    // DELETE APPOINTMENT - GOOD PATH
    @Test
    void deleteAppointmentById_Deletes_WhenAuthorized() {
        mockSecurityUser(7L);

        Long id = 1L;
        Appointment appointment = new Appointment();
        appointment.setUserId(7L);

        when(appointmentMapper.getById(id)).thenReturn(appointment);
        when(appointmentMapper.deleteAppointmentById(id)).thenReturn(1);

        appointmentService.deleteAppointmentById(id);

        verify(appointmentMapper).deleteAppointmentById(id);
    }


}
