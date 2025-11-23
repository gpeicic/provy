package com.example.provy.appointment;

import com.example.provy.appointment.exception.InvalidAppointmentTimeException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentValidator {

    private final AppointmentMapper appointmentMapper;

    public AppointmentValidator(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    public void isAppointmentAvailable(Appointment appointment){
        List<Appointment> appointments = appointmentMapper.getAppointmentsByProvider(appointment.getProviderProfileId());
        for(Appointment a : appointments){
            boolean sameDate = a.getDate().isEqual(appointment.getDate());
            boolean confirmed = a.getAppointmentStatus() == AppointmentStatus.CONFIRMED;
            boolean overlaps = !appointment.getEndTime().isBefore(a.getStartTime()) &&
                    !appointment.getStartTime().isAfter(a.getEndTime());

            if(sameDate && confirmed && overlaps){
                throw new InvalidAppointmentTimeException("The appointment slot from " +
                        appointment.getStartTime() + " to " + appointment.getEndTime() + " is already taken");
            }
        }


    }
}
