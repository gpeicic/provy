package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.providerProfile.DTO.ProviderProfileRequestDTO;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.providerProfile.exception.InvalidWorkingHoursException;
import com.example.provy.providerProfile.exception.ProviderAlreadyExists;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.providerProfile.providerWorkingHour.ProviderWorkingHour;
import com.example.provy.providerProfile.providerWorkingHour.ProviderWorkingHourService;
import com.example.provy.role.RoleMapper;
import com.example.provy.security.CustomUserDetails;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.UserService;
import com.example.provy.user.exception.RoleNotFoundException;
import com.example.provy.user.exception.UserAlreadyExistsAException;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;
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
    private final UserService userService;
    private final ProviderProfileDTOMapper providerProfileDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public ProviderProfileServiceImpl(UserMapper userMapper, RoleMapper roleMapper, ProviderProfileMapper providerProfileMapper,
                                      ProviderWorkingHourService providerWorkingHourService, UserDTOMapper userDTOMapper,
                                      UserService userService, ProviderProfileDTOMapper providerProfileDTOMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.providerProfileMapper = providerProfileMapper;
        this.providerWorkingHourService = providerWorkingHourService;
        this.userDTOMapper = userDTOMapper;
        this.userService = userService;
        this.providerProfileDTOMapper = providerProfileDTOMapper;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userService.registerUser(registerUser);

        Long roleProviderId = roleMapper.getRoleIdByName("ROLE_PROVIDER");

        if(roleProviderId == null){
            throw new RoleNotFoundException("ROLE_PROVIDER");
        }

        userMapper.insertUserRole(registeredUser.getId(),roleProviderId);

        //creating providerProfile
        ProviderProfileRequestDTO profileDto = request.getProviderProfile();

        if(providerProfileMapper.getCountByBusinessName(profileDto.getBusinessName()) > 0){
            throw new ProviderAlreadyExists(profileDto.getBusinessName());
        }

        ProviderProfile profile = providerProfileDTOMapper.toEntity(profileDto);
        profile.setUserId(registeredUser.getId());
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

        ProviderProfile profile = providerProfileMapper.getByProviderId(id);
        if(profile == null){
            throw new ProviderNotFoundException(id);
        }
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long currentUserId = currentUser.getId();

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin && !profile.getUserId().equals(currentUserId)){
            throw new AccessDeniedException("You are not allowed to delete this provider profile.");
        }

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
