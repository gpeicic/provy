package com.example.provy.notification;

import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NotificationMapper {
    @Insert("INSERT INTO notification(user_id, appointment_id, message, scheduled_time, sent) " +
            "VALUES(#{userId}, #{appointmentId}, #{message}, #{scheduledTime}, #{sent})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertNotification(Notification notification);

    @Select("SELECT * FROM notification WHERE user_id = #{userId} AND sent = false")
    List<Notification> getPendingNotificationsForUser(Long userId);

    @Select("SELECT * FROM notification ")
    List<Notification> getAllPendingNotifications();

    @Update("UPDATE notification SET sent = true WHERE id = #{id}")
    void markAsSent(Long id);
}
