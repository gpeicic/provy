package com.example.provy.User;

import com.example.provy.Role.RoleMapper;
import com.example.provy.User.DTO.UserDTOMapper;
import com.example.provy.User.DTO.UserRequestDTO;
import com.example.provy.User.DTO.UserResponseDTO;
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
        return userDTOMapper.toResponseDTO(userMapper.getUserById(id));
    }
    @Override
    public void registerUser(UserRequestDTO userRequestDTO){
        User user = userDTOMapper.toEntity(userRequestDTO);
        userMapper.registerUser(user);

        Long roleUserId = roleMapper.getRoleIdByName("ROLE_USER");
        userMapper.insertUserRole(user.getId(),roleUserId);
    }
    @Override
    public void deleteUser(Long id){
        userMapper.deleteUserById(id);
    }
}
