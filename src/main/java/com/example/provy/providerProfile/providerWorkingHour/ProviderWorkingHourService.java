package com.example.provy.providerProfile.providerWorkingHour;

import java.util.List;

public interface ProviderWorkingHourService {

    List<ProviderWorkingHour> getByProviderProfileId(Long id);
    void addProviderWorkingHour(List<ProviderWorkingHour> hours);
    void deleteProviderWorkingHour(Long id);
}
