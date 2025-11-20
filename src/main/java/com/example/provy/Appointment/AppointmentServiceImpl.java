package com.example.provy.Appointment;

import com.example.provy.ProviderOffering.ProviderOffering;
import com.example.provy.ProviderOffering.ProviderOfferingMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Primary
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentMapper appointmentMapper;
    private final ProviderOfferingMapper providerOfferingMapper;


    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, ProviderOfferingMapper providerOfferingMapper){
        this.appointmentMapper = appointmentMapper;
        this.providerOfferingMapper = providerOfferingMapper;
    }
    @Override
    public Appointment getById(Long id){
        return appointmentMapper.getById(id);
    }
    @Override
    public void bookAppointment(Appointment appointment){
       ProviderOffering providerOffering = providerOfferingMapper.getById(appointment.getProviderOfferingId());
       LocalTime calculatedTime = appointment.getStartTime().plusMinutes(providerOffering.getDurationInMinutes());
       appointment.setEndTime(calculatedTime);

       if(!isAppointmentAvailable(appointment)){
           appointment.setAppointmentStatus(AppointmentStatus.REJECTED);
       }
       else{
           appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
       }

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
        appointmentMapper.deleteAppointmentById(id);
    }
}
