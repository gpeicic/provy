package com.example.provy.user;

import com.example.provy.security.AuthorizationService;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.exception.UserNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDTOMapper userDTOMapper;
    private final UserRegistrationService userRegistrationService;

    private static final String USER_AUTHORIZE_ERROR = "You do not have permission to access this user.";
    private static final String USER_PROFILE_DELETE_ERROR = "You are not allowed to delete this user profile.";

    public UserServiceImpl(UserMapper userMapper, UserDTOMapper userDTOMapper, UserRegistrationService userRegistrationService) {
        this.userMapper = userMapper;
        this.userDTOMapper = userDTOMapper;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public UserResponseDTO getUserById(Long id){

        User user = userMapper.getUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }

        AuthorizationService.authorizeCurrentUserOrAdmin(id,USER_AUTHORIZE_ERROR);

        return userDTOMapper.toResponseDTO(user);
    }
    @Override
    public User getUserByEmail(String email){
        User user = userMapper.getUserByEmail(email);
        if(user == null){
            throw new UserNotFoundException(0L);
        }
        return user;
    }
    @Override
    public User registerUser(UserRequestDTO userRequestDTO){
        return userRegistrationService.registerUser(userRequestDTO);
    }
    @Override
    public void deleteUser(Long id){
        AuthorizationService.authorizeCurrentUserOrAdmin(id, USER_PROFILE_DELETE_ERROR);

        int deleted = userMapper.deleteUserById(id);
        if(deleted == 0) throw new UserNotFoundException(id);
    }
    @Override
    public void insertRole(Long userId, Long roleId){
        userMapper.insertUserRole(userId, roleId);
    }

}
