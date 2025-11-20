package com.example.provy.ProviderProfile;

import com.example.provy.ProviderProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.ProviderProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.ProviderProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHour;
import com.example.provy.ProviderProfile.ProviderWorkingHour.ProviderWorkingHourService;
import com.example.provy.Role.RoleMapper;
import com.example.provy.User.DTO.UserDTOMapper;
import com.example.provy.User.DTO.UserRequestDTO;
import com.example.provy.User.User;
import com.example.provy.User.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return providerProfileDTOMapper.toResponseDTO(providerProfileMapper.getByProviderId(id));
    }
    @Override
    public ProviderProfile registerProviderProfile(ProviderRegistrationRequest request){
        //creating user
        UserRequestDTO registerUser = new UserRequestDTO();
        registerUser.setEmail(request.getUser().getEmail());
        registerUser.setPassword(request.getUser().getPassword());
        registerUser.setIme(request.getUser().getIme());
        registerUser.setPrezime(request.getUser().getPrezime());
        User user =userDTOMapper.toEntity(registerUser);


        userMapper.registerUser(user);

        Long roleProviderId = roleMapper.getRoleIdByName("ROLE_PROVIDER");
        userMapper.insertUserRole(user.getId(),roleProviderId);

        //creating providerProfile
        ProviderProfile registerProfile = new ProviderProfile();
        registerProfile.setUserId(user.getId());
        registerProfile.setBusinessName(request.getProviderProfile().getBusinessName());
        registerProfile.setAddress(request.getProviderProfile().getAddress());
        registerProfile.setPhone(request.getProviderProfile().getPhone());
        registerProfile.setDescription(request.getProviderProfile().getDescription());
        registerProfile.setStatus(ProviderStatus.PENDING);

        providerProfileMapper.registerProviderProfile(registerProfile);

        if(request.getWorkingHours() != null){
            List<ProviderWorkingHour> hours = request.getWorkingHours().stream()
                    .map(h -> {
                        ProviderWorkingHour hour = new ProviderWorkingHour();
                        hour.setProviderProfileId(registerProfile.getId());
                        hour.setDayOfWeek(h.getDayOfWeek());
                        hour.setStartTime(h.getStartTime());
                        hour.setEndTime(h.getEndTime());
                        return hour;
                    })
                    .toList();
            providerWorkingHourService.addProviderWorkingHour(hours);
        }
        return registerProfile;
    }
    @Override
    public void deleteProviderProfileById(Long id){
        providerProfileMapper.deleteProviderProfileById(id);
    }
}
