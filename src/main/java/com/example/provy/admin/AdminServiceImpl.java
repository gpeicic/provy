package com.example.provy.admin;

import com.example.provy.providerProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.ProviderProfile;
import com.example.provy.providerProfile.ProviderProfileMapper;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.role.RoleMapper;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import com.example.provy.user.exception.RoleNotFoundException;
import com.example.provy.user.exception.UserAlreadyExistsAException;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Primary
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserMapper userMapper;
    private final ProviderProfileMapper providerProfileMapper;
    private final UserDTOMapper userDTOMapper;
    private final ProviderProfileDTOMapper providerProfileDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public AdminServiceImpl(UserMapper userMapper, ProviderProfileMapper providerProfileMapper,
                            UserDTOMapper userDTOMapper, ProviderProfileDTOMapper providerProfileDTOMapper,
                            PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.providerProfileMapper = providerProfileMapper;
        this.userDTOMapper = userDTOMapper;
        this.providerProfileDTOMapper = providerProfileDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
      List<User> users = userMapper.getAllUsers();
        return users.stream()
                .map(userDTOMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return userDTOMapper.toResponseDTO(userMapper.getUserById(id));
    }

    @Override
    public List<ProviderProfileResponseDTO> getAllProviderProfiles() {
        List<ProviderProfile> profiles = providerProfileMapper.getAllProviders();
        return profiles.stream()
                .map(providerProfileDTOMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User createAdmin(UserRequestDTO userRequestDTO) {
        if(userMapper.getEmailByEmail(userRequestDTO.getEmail()) != null){
            throw new UserAlreadyExistsAException(userRequestDTO.getEmail());
        }

        User user = userDTOMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.registerUser(user);

        Long roleUserId = roleMapper.getRoleIdByName("ROLE_ADMIN");
        if(roleUserId == null){
            throw new RoleNotFoundException("ROLE_ADMIN\"");
        }

        userMapper.insertUserRole(user.getId(),roleUserId);
        return user;
    }

    @Override
    public ProviderProfileResponseDTO getProviderById(Long id) {
        ProviderProfile profile = providerProfileMapper.getByProviderId(id);
        return providerProfileDTOMapper.toResponseDTO(profile);
    }

    @Override
    public void deleteUser(Long id) {
        int deleted = userMapper.deleteUserById(id);
        if(deleted == 0 ){
            throw new ProviderNotFoundException(id);
        }
    }
    @Override
    public void deleteProvider(Long id) {
        int deleted = providerProfileMapper.deleteProviderProfileById(id);
        if(deleted == 0 ){
            throw new ProviderNotFoundException(id);
        }
    }
}
