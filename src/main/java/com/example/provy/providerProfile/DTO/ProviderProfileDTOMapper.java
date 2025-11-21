package com.example.provy.providerProfile.DTO;

import com.example.provy.providerProfile.ProviderProfile;
import com.example.provy.providerProfile.providerWorkingHour.ProviderWorkingHourService;
import com.example.provy.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class ProviderProfileDTOMapper {

    private final UserService userService;
    private final ProviderWorkingHourService workingHourService;

    public ProviderProfileDTOMapper(UserService userService, ProviderWorkingHourService workingHourService) {
        this.userService = userService;
        this.workingHourService = workingHourService;
    }
    public ProviderProfile toEntity(ProviderProfileRequestDTO dto){
        ProviderProfile profile = new ProviderProfile();
        profile.setUserId(dto.getUser_id());
        profile.setDescription(dto.getDescription());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setBusinessName(dto.getBusinessName());

        return profile;
    }

    public ProviderProfileRequestDTO toRequestDTO(ProviderProfile profile){
        ProviderProfileRequestDTO dto = new ProviderProfileRequestDTO();
        dto.setUser_id(profile.getUserId());
        dto.setAddress(profile.getAddress());
        dto.setPhone(profile.getPhone());
        dto.setBusinessName(profile.getBusinessName());
        dto.setDescription(profile.getDescription());

        return dto;
    }
    public ProviderProfileResponseDTO toResponseDTO(ProviderProfile profile) {
        ProviderProfileResponseDTO dto = new ProviderProfileResponseDTO();

        dto.setUser(userService.getUserById(profile.getUserId()));

        ProviderProfileRequestDTO profileDTO = new ProviderProfileRequestDTO();
        profileDTO.setUser_id(profile.getUserId());
        profileDTO.setBusinessName(profile.getBusinessName());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setPhone(profile.getPhone());
        profileDTO.setDescription(profile.getDescription());
        dto.setProviderProfile(profileDTO);

        dto.setWorkingHours(workingHourService.getByProviderProfileId(profile.getId()));

        return dto;
    }

}
