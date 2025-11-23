package com.example.provy.user;

import com.example.provy.role.RoleMapper;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.exception.RoleNotFoundException;
import com.example.provy.user.exception.UserAlreadyExistsAException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserMapper userMapper;
    private final UserDTOMapper userDTOMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserMapper userMapper, UserDTOMapper userDTOMapper, RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userDTOMapper = userDTOMapper;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUserForProvider(UserRequestDTO dto){
        if(userMapper.getUserByEmail(dto.getEmail()) != null){
            throw new UserAlreadyExistsAException(dto.getEmail());
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userDTOMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userMapper.registerUser(user);

        Long roleId = roleMapper.getRoleIdByName("ROLE_PROVIDER");
        if(roleId == null){
            throw new RoleNotFoundException("ROLE_PROVIDER");
        }
        userMapper.insertUserRole(user.getId(), roleId);

        return user;
    }
    public User registerUser(UserRequestDTO userRequestDTO){
        if(userMapper.getEmailByEmail(userRequestDTO.getEmail()) != null){
            throw new UserAlreadyExistsAException(userRequestDTO.getEmail());
        }
        User user = userDTOMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.registerUser(user);

        Long roleUserId = roleMapper.getRoleIdByName("ROLE_USER");
        if(roleUserId == null){
            throw new RoleNotFoundException("ROLE_USER");
        }
        userMapper.insertUserRole(user.getId(),roleUserId);
        return user;
    }

}
