package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.providerProfile.providerWorkingHour.ProviderWorkingHourService;
import com.example.provy.user.User;
import com.example.provy.user.UserRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class ProviderRegistrationService {
    private final UserRegistrationService userRegistrationService;
    private final ProviderProfileFactory providerProfileFactory;
    private final ProviderWorkingHourService providerWorkingHourService;

    public ProviderRegistrationService(UserRegistrationService userRegistrationService, ProviderProfileFactory providerProfileFactory,
                                       ProviderWorkingHourService providerWorkingHourService) {
        this.userRegistrationService = userRegistrationService;
        this.providerProfileFactory = providerProfileFactory;
        this.providerWorkingHourService = providerWorkingHourService;
    }

    public ProviderProfile registerProvider(ProviderRegistrationRequest request){
        User user = userRegistrationService.registerUserForProvider(request.getUser());

        ProviderProfile profile = providerProfileFactory.createProfile(user.getId(), request.getProviderProfile());
        providerWorkingHourService.addProviderWorkingHour(request.getWorkingHours());

        return profile;
    }
}
