package com.example.provy.ProviderProfile.ProviderWorkingHour;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            providerWorkingHourMapper.addProviderWorkingHour(h);
        }
    }
    @Override
    public void deleteProviderWorkingHour(Long id){
        providerWorkingHourMapper.deleteProviderWorkingHour(id);
    }
}
