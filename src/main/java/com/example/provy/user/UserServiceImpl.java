package com.example.provy.user;

import com.example.provy.appointment.AppointmentServiceImpl;
import com.example.provy.role.RoleMapper;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.exception.RoleNotFoundException;
import com.example.provy.user.exception.UserAlreadyExistsAException;
import com.example.provy.user.exception.UserNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper,UserDTOMapper userDTOMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserResponseDTO getUserById(Long id){

        User user = userMapper.getUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }

        authorizeCurrentUserOrAdmin(user);

        return userDTOMapper.toResponseDTO(user);
    }
    @Override
    public User getUserByEmail(String email){
        User user = userMapper.getUserByEmail(email);
        if(user == null){
            throw new UserNotFoundException(user.getId());
        }
        return user;
    }
    @Override
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
    @Override
    public void deleteUser(Long id){
        User user = userMapper.getUserById(id);

        authorizeCurrentUserOrAdmin(user);

        int deleted = userMapper.deleteUserById(id);
        if(deleted == 0) throw new UserNotFoundException(id);
    }
    private void authorizeCurrentUserOrAdmin(User user) {
        AppointmentServiceImpl.authorizeCurrentUserOrAdmin(user);
    }
}
