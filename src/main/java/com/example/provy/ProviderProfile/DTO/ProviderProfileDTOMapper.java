package com.example.provy.ProviderProfile.DTO;

import com.example.provy.ProviderProfile.ProviderProfile;
import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHourService;
import com.example.provy.User.UserService;
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
        profile.setUser_id(dto.getUser_id());
        profile.setDescription(dto.getDescription());
        profile.setStatus(dto.getStatus());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setBusinessName(dto.getBusinessName());

        return profile;
    }

    public ProviderProfileRequestDTO toRequestDTO(ProviderProfile profile){
        ProviderProfileRequestDTO dto = new ProviderProfileRequestDTO();
        dto.setUser_id(profile.getUser_id());
        dto.setAddress(profile.getAddress());
        dto.setPhone(profile.getPhone());
        dto.setBusinessName(profile.getBusinessName());
        dto.setDescription(profile.getDescription());
        dto.setStatus(profile.getStatus());

        return dto;
    }
    public ProviderProfileResponseDTO toResponseDTO(ProviderProfile profile) {
        ProviderProfileResponseDTO dto = new ProviderProfileResponseDTO();

        dto.setUser(userService.getUserById(profile.getUser_id()));

        ProviderProfileRequestDTO profileDTO = new ProviderProfileRequestDTO();
        profileDTO.setBusinessName(profile.getBusinessName());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setPhone(profile.getPhone());
        profileDTO.setDescription(profile.getDescription());
        profileDTO.setStatus(profile.getStatus());
        dto.setProviderProfile(profileDTO);

        dto.setWorkingHours(workingHourService.getByProviderProfileId(profile.getId()));

        return dto;
    }

}
