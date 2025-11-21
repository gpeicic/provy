package com.example.provy.ProviderProfile;

import com.example.provy.ProviderProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.ProviderProfile.DTO.ProviderProfileRequestDTO;
import com.example.provy.ProviderProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.ProviderProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.ProviderProfile.Exception.InvalidWorkingHoursException;
import com.example.provy.ProviderProfile.Exception.ProviderAlreadyExists;
import com.example.provy.ProviderProfile.Exception.ProviderNotFoundException;
import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHour;
import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHourService;
import com.example.provy.Role.RoleMapper;
import com.example.provy.User.DTO.UserDTOMapper;
import com.example.provy.User.DTO.UserRequestDTO;
import com.example.provy.User.Exception.RoleNotFoundException;
import com.example.provy.User.Exception.UserAlreadyExistsAException;
import com.example.provy.User.User;
import com.example.provy.User.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
@Primary
@Service
@Transactional
public class ProviderProfileServiceImpl implements ProviderProfileService{
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ProviderProfileMapper providerProfileMapper;
    private final ProviderWorkingHourService providerWorkingHourService;
    private final UserDTOMapper userDTOMapper;
    private final ProviderProfileDTOMapper providerProfileDTOMapper;

    public ProviderProfileServiceImpl(ProviderProfileMapper providerProfileMapper, UserMapper userMapper, RoleMapper roleMapper,
                                      ProviderWorkingHourService providerWorkingHourService, ProviderProfileDTOMapper providerProfileDTOMapper,
                                      UserDTOMapper userDTOMapper){
        this.providerProfileMapper = providerProfileMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.providerWorkingHourService = providerWorkingHourService;
        this.providerProfileDTOMapper = providerProfileDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public ProviderProfileResponseDTO getByProviderId(Long id){
        ProviderProfile profile = providerProfileMapper.getByProviderId(id);
        if(profile == null){
            throw new ProviderNotFoundException(id);
        }

        return providerProfileDTOMapper.toResponseDTO(providerProfileMapper.getByProviderId(id));
    }
    @Override
    public ProviderProfile registerProviderProfile(ProviderRegistrationRequest request){
        //creating user
        UserRequestDTO registerUser = request.getUser();

        if(userMapper.getEmailByEmail(registerUser.getEmail()) != null){
            throw new UserAlreadyExistsAException(registerUser.getEmail());
        }

        User user =userDTOMapper.toEntity(registerUser);
        userMapper.registerUser(user);

        Long roleProviderId = roleMapper.getRoleIdByName("ROLE_PROVIDER");
        if(roleProviderId == null){
            throw new RoleNotFoundException("ROLE_PROVIDER");
        }
        userMapper.insertUserRole(user.getId(),roleProviderId);

        //creating providerProfile
        ProviderProfileRequestDTO profileDto = request.getProviderProfile();
        if(providerProfileMapper.getCountByBusinessName(profileDto.getBusinessName()) == 0){
            throw new ProviderAlreadyExists(profileDto.getBusinessName());
        }
        ProviderProfile profile = providerProfileDTOMapper.toEntity(profileDto);
        providerProfileMapper.registerProviderProfile(profile);

        // Working Hours
        if(request.getWorkingHours() != null){
            List<ProviderWorkingHour> hours = request.getWorkingHours().stream()
                    .map(h -> {
                        //validation
                        validateWorkingHour(h);

                        ProviderWorkingHour hour = new ProviderWorkingHour();
                        hour.setProviderProfileId(profile.getId());
                        hour.setDayOfWeek(h.getDayOfWeek());
                        hour.setStartTime(h.getStartTime());
                        hour.setEndTime(h.getEndTime());
                        return hour;
                    })
                    .toList();
            providerWorkingHourService.addProviderWorkingHour(hours);
        }
        return profile;
    }
    @Override
    public void deleteProviderProfileById(Long id){

        int deleted = providerProfileMapper.deleteProviderProfileById(id);
        if (deleted == 0){
            throw new ProviderNotFoundException(id);
        }
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
