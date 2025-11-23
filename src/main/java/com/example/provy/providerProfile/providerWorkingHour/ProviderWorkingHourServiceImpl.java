package com.example.provy.providerProfile.providerWorkingHour;

import com.example.provy.providerProfile.exception.InvalidWorkingHoursException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
@Primary
@Transactional
@Service
public class ProviderWorkingHourServiceImpl implements ProviderWorkingHourService{
    private final ProviderWorkingHourMapper providerWorkingHourMapper;

    public ProviderWorkingHourServiceImpl(ProviderWorkingHourMapper providerWorkingHourMapper){
        this.providerWorkingHourMapper = providerWorkingHourMapper;
    }
    @Override
    public List<ProviderWorkingHour> getByProviderProfileId(Long id){
        return providerWorkingHourMapper.getByProviderProfileId(id);
    }
    @Override
    public void addProviderWorkingHour(List<ProviderWorkingHour> hours){
        for(ProviderWorkingHour h : hours){
            validateWorkingHour(h);
            providerWorkingHourMapper.addProviderWorkingHour(h);
        }
    }
    @Override
    public void deleteProviderWorkingHour(Long id){
        providerWorkingHourMapper.deleteProviderWorkingHour(id);
    }


    private void validateWorkingHour(ProviderWorkingHour h){
        LocalTime start = h.getStartTime();
        LocalTime end = h.getEndTime();

        if(start == null || end == null) throw new InvalidWorkingHoursException("Start time and end time must be set.");

        Long minutes;

        if(!end.isBefore(start)){
            minutes = Duration.between(start,end).toMinutes();
        }
        else{
            minutes = Duration.between(start,LocalTime.MIDNIGHT).toMinutes()
                    + Duration.between(LocalTime.MIDNIGHT, end).toMinutes();
        }

        if(minutes < 30){
            throw new InvalidWorkingHoursException("Shift must be at least 30 minutes long.");
        }
        if(minutes > 24*60){
            throw new InvalidWorkingHoursException("Shift cannot exceed 24 hours.");
        }
    }
}
