package com.example.provy.user;

import com.example.provy.security.AuthorizationService;
import com.example.provy.user.DTO.UserDTOMapper;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import com.example.provy.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserDTOMapper userDTOMapper;
    @Mock
    private UserRegistrationService userRegistrationService;
    private UserServiceImpl userService;

    @BeforeEach
    void setup(){
        userService = new UserServiceImpl(userMapper,userDTOMapper,userRegistrationService);
    }

    // GET USER BY ID - GOOD PATH
    @Test
    void getUserById_ReturnsDTO_WhenUserExistsAndAuthorized(){
        Long id = 1L;

        User mockUser = new User();
        UserResponseDTO mockDTO = new UserResponseDTO();

        when(userMapper.getUserById(id)).thenReturn(mockUser);
        when(userDTOMapper.toResponseDTO(mockUser)).thenReturn(mockDTO);

        try(MockedStatic<AuthorizationService> auth = Mockito.mockStatic(AuthorizationService.class)){
            auth.when(() -> AuthorizationService.authorizeCurrentUserOrAdmin(eq(id),anyString()))
                    .thenAnswer(invocationOnMock -> null);

            UserResponseDTO result = userService.getUserById(id);

            assertEquals(mockDTO,result);
            auth.verify(() -> AuthorizationService.authorizeCurrentUserOrAdmin(id, "You do not have permission to access this user."));
        }
    }

    // GET USER BY ID - USER NOT FOUND

    @Test
    void getUserById_ThrowsException_WhenUserDoesNotExist(){
        Long id = 1L;

        when(userMapper.getUserById(id)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
        verifyNoInteractions(userDTOMapper);

    }

    // GET USER BY EMAIL - GOOD PATH

    @Test
    void getUserByEmail_ReturnsUser_WhenExists(){
        User user = new User();
        when(userMapper.getUserByEmail("test@mail.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@mail.com");

        assertEquals(user,result);
    }

    // GET USER BY EMAIL - USER NOT FOUND

    @Test
    void getUserByEmail_ThrowsException_WhnUserNotFound(){

        when(userMapper.getUserByEmail("test@mail.com")).thenReturn(null);

        assertThrows(UserNotFoundException.class,() -> userService.getUserByEmail("test@mail.com"));
    }

    // REGISTER USER - DELEGATED

    @Test
    void registerUser_DelegatesToRegistrationService(){
        UserRequestDTO requestDTO = new UserRequestDTO();
        User user = new User();

        when(userRegistrationService.registerUser(requestDTO)).thenReturn(user);

        User result = userService.registerUser(requestDTO);

        assertEquals(user, result);
        verify(userRegistrationService).registerUser(requestDTO);
    }

    // DELETE USER - GOOD PATH

    @Test
    void deleteUser_Deletes_WhenAuthorizedAndExists(){

        Long id = 1L;

        when(userMapper.deleteUserById(id)).thenReturn(1);

        try(MockedStatic<AuthorizationService> auth = Mockito.mockStatic(AuthorizationService.class)){
            auth.when(() -> AuthorizationService.authorizeCurrentUserOrAdmin(eq(id), anyString()))
                    .thenAnswer(invocationOnMock -> null);

            assertDoesNotThrow(() -> userService.deleteUser(id));
            verify(userMapper).deleteUserById(id);
        }
    }
}
