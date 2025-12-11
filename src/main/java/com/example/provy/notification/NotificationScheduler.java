package com.example.provy.notification;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class NotificationScheduler {
    private final NotificationMapper notificationMapper;

    public NotificationScheduler(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Scheduled(fixedRate =3_600_000)
    public void sendPendingNotifications(){
        List<Notification> pendingNotifications = notificationMapper.getAllPendingNotifications();

        for(Notification n : pendingNotifications){
            System.out.println(n.getMessage());
            notificationMapper.markAsSent(n.getId());
        }

    }
}
