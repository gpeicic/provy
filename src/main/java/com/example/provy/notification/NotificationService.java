package com.example.provy.notification;

import com.example.provy.appointment.Appointment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
@Transactional
public class NotificationService {
    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public void createNotificationsForAppointment(Appointment appointment){
        Notification n1 = new Notification();
        n1.setUserId(appointment.getUserId());
        n1.setMessage("Reminder: appointment tomorrow at " + appointment.getStartTime());
        n1.setScheduledTime(appointment.getDate().atTime(appointment.getStartTime()));
        n1.setSent(false);
        notificationMapper.insertNotification(n1);

        Notification n2 = new Notification();
        n2.setUserId(appointment.getUserId());
        n2.setAppointmentId(appointment.getId());
        n2.setMessage("Reminder: appointment in 3 hours at " + appointment.getStartTime());
        n2.setScheduledTime(appointment.getDate().atTime(appointment.getStartTime()).minusHours(3));
        n2.setSent(false);
        notificationMapper.insertNotification(n2);
    }

    public List<Notification> getPendingNotificationsForUser(Long userId){
        return notificationMapper.getPendingNotificationsForUser(userId);
    }

    public void markAsSent(Long notificationId){
        notificationMapper.markAsSent(notificationId);
    }
}
