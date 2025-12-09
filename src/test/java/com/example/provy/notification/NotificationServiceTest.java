package com.example.provy.notification;

import com.example.provy.appointment.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private NotificationMapper notificationMapper;
    private NotificationService notificationService;

    @BeforeEach
    void setup(){
        notificationService = new NotificationService(notificationMapper);
    }
    // CREATE NOTIFICATION - GOOD PATH
    @Test
    void createNotificationsForAppointment(){
        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setUserId(5L);
        appointment.setDate(LocalDate.of(2025, 3, 15));
        appointment.setStartTime(LocalTime.of(14, 0));


        notificationService.createNotificationsForAppointment(appointment);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationMapper,times(2)).insertNotification(captor.capture());
        List<Notification> notifications = captor.getAllValues();

        // FIRST NOTIFICATION
        Notification n1 = notifications.get(0);
        assertEquals(5L, n1.getUserId());
        assertEquals("Reminder: appointment tomorrow at 14:00", n1.getMessage());
        assertEquals(LocalDate.of(2025, 3, 15).atTime(14, 0), n1.getScheduledTime());
        assertFalse(n1.isSent());

        // SECOND NOTIFICATION
        Notification n2 = notifications.get(1);
        assertEquals(5L, n2.getUserId());
        assertEquals(10L, n2.getAppointmentId());
        assertEquals("Reminder: appointment in 3 hours at 14:00", n2.getMessage());
        assertEquals(LocalDate.of(2025, 3, 15).atTime(14, 0).minusHours(3), n2.getScheduledTime());
        assertFalse(n2.isSent());
    }

    // GET PENDING FOR USER - GOOD PATH
    @Test
    void getPendingNotificationsForUser_ReturnsList() {
        List<Notification> mockList = List.of(new Notification(), new Notification());
        when(notificationMapper.getPendingNotificationsForUser(5L)).thenReturn(mockList);

        List<Notification> result = notificationService.getPendingNotificationsForUser(5L);

        assertEquals(2, result.size());
        verify(notificationMapper).getPendingNotificationsForUser(5L);
    }
    // MARK AS SENT - GOOD PATH
    @Test
    void markAsSent_InvokesMapper() {
        notificationService.markAsSent(99L);

        verify(notificationMapper).markAsSent(99L);
    }
}
