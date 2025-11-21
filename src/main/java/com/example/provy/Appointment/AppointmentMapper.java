package com.example.provy.Appointment;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {

    @Select("SELECT * FROM appointment WHERE provider_profile_id = #{provider_id}")
    @Results(id = "AppointmentResultMap",value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "providerOfferingId", column = "provider_offering_id"),
            @Result(property = "providerProfileId", column = "provider_profile_id"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "date", column = "date"),
            @Result(property = "appointmentStatus", column = "appointment_status")
    })
    List<Appointment> getAppointmentsByProvider(@Param("provider_id") Long id);
    @Select("SELECT * FROM appointment WHERE id = #{id}")
    @ResultMap("AppointmentResultMap")
    Appointment getById(@Param("id") Long id);

    @Insert("INSERT INTO appointment(user_id,provider_offering_id,provider_profile_id,start_time,end_time,date,appointment_status) VALUES" +
            "(#{userId}, #{providerOfferingId}, #{providerProfileId},#{startTime}, #{endTime},#{date},#{appointmentStatus})")
    void bookAppointment(Appointment appointment);

    @Delete("DELETE FROM appointment WHERE id = #{id}")
    int deleteAppointmentById(@Param("id") Long id);
}
