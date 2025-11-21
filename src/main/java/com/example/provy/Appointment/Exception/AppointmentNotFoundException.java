package com.example.provy.Appointment.Exception;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(Long id){
        super("Appointment with id " + id + " not found.");
    }
}
