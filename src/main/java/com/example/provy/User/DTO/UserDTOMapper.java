package com.example.provy.User.DTO;

import com.example.provy.User.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public User toEntity(UserRequestDTO dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setIme(dto.getIme());
        user.setPrezime(dto.getPrezime());

        return user;
    }

    public UserRequestDTO toRequestDTO(User user) {
        return new UserRequestDTO(
                user.getEmail(),
                user.getPassword(),
                user.getIme(),
                user.getPrezime()
        );
    }

    public User toEntity(UserResponseDTO dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setIme(dto.getIme());
        user.setPrezime(dto.getPrezime());
        user.setRoles(dto.getRoles());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user){
        UserResponseDTO responseDTO = new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getIme(),
                user.getPrezime(),
                user.getRoles()
        );


        return responseDTO;
    }

}
