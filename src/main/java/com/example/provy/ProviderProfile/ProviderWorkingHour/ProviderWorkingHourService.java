package com.example.provy.ProviderProfile.ProviderWorkingHour;

import java.util.List;

public interface ProviderWorkingHourService {

    List<ProviderWorkingHour> getByProviderProfileId(Long id);
    void addProviderWorkingHour(List<ProviderWorkingHour> hours);
    void deleteProviderWorkingHour(Long id);
}
