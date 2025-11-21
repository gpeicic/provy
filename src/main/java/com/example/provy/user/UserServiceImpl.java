package com.example.provy.user;

import com.example.provy.role.RoleMapper;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.exception.RoleNotFoundException;
import com.example.provy.user.exception.UserAlreadyExistsAException;
import com.example.provy.user.exception.UserNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserDTOMapper userDTOMapper;

    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper,UserDTOMapper userDTOMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userDTOMapper = userDTOMapper;
    }
    @Override
    public UserResponseDTO getUserById(Long id){
        User user = userMapper.getUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return userDTOMapper.toResponseDTO(user);
    }
    @Override
    public void registerUser(UserRequestDTO userRequestDTO){
        if(userMapper.getEmailByEmail(userRequestDTO.getEmail()) != null){
            throw new UserAlreadyExistsAException(userRequestDTO.getEmail());
        }
        User user = userDTOMapper.toEntity(userRequestDTO);
        userMapper.registerUser(user);

        Long roleUserId = roleMapper.getRoleIdByName("ROLE_USER");
        if(roleUserId == null){
            throw new RoleNotFoundException("ROLE_USER");
        }
        userMapper.insertUserRole(user.getId(),roleUserId);
    }
    @Override
    public void deleteUser(Long id){
        int deleted = userMapper.deleteUserById(id);
        if(deleted == 0) throw new UserNotFoundException(id);
    }
}
