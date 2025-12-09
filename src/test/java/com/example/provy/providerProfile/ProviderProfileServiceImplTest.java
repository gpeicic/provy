package com.example.provy.providerProfile;

import com.example.provy.providerProfile.DTO.ProviderProfileDTOMapper;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.providerProfile.exception.ProviderNotFoundException;
import com.example.provy.security.AuthorizationService;
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
public class ProviderProfileServiceImplTest {

    @Mock
    private ProviderRegistrationService providerRegistrationService;
    @Mock
    private ProviderProfileMapper providerProfileMapper;
    @Mock
    private ProviderProfileDTOMapper profileDTOMapper;
    private ProviderProfileServiceImpl providerProfileService;

    @BeforeEach
    void setup(){
        providerProfileService = new ProviderProfileServiceImpl(providerRegistrationService, providerProfileMapper, profileDTOMapper);
    }

    // GET PROVIDER BY ID - GOOD PATH
    @Test
    void getByProviderId_ReturnsDTO_whenProviderExistsAndAuthorized(){
        Long id = 1L;
        ProviderProfile mockProfile = Mockito.mock(ProviderProfile.class);
        ProviderProfileResponseDTO dto = new ProviderProfileResponseDTO();

        when(providerProfileMapper.getByProviderId(id)).thenReturn(mockProfile);
        when(profileDTOMapper.toResponseDTO(mockProfile)).thenReturn(dto);
        when(mockProfile.getUserId()).thenReturn(1L);

        try(MockedStatic<AuthorizationService> auth = Mockito.mockStatic(AuthorizationService.class)){
            auth.when(() -> AuthorizationService.authorizeCurrentUserOrAdmin(1L, "You do not have permission to access this provider."))
                    .thenAnswer(invocationOnMock -> null);

            ProviderProfileResponseDTO result = providerProfileService.getByProviderId(id);

            assertEquals(dto, result);
            auth.verify(() -> AuthorizationService.authorizeCurrentUserOrAdmin(id, "You do not have permission to access this provider."));
        }

    }

    // GET PROVIDER BY ID - PROVIDER NOT FOUND
    @Test
    void getByProviderId_ThrowsException_WhenProviderDoesNotExists(){
        Long id = 1L;

        when(providerProfileMapper.getByProviderId(id)).thenReturn(null);
        assertThrows(ProviderNotFoundException.class, ()-> providerProfileService.getByProviderId(id));
        verifyNoInteractions(profileDTOMapper);
    }

    // REGISTER PROVIDER - DELEGATED
    @Test
    void registerProvider_DelegatesToRegistrationService(){
        ProviderRegistrationRequest request = Mockito.mock(ProviderRegistrationRequest.class);
        ProviderProfile profile = new ProviderProfile();
        when(providerRegistrationService.registerProvider(request)).thenReturn(profile);

        ProviderProfile result = providerProfileService.registerProviderProfile(request);
        assertEquals(profile,result);
        verify(providerRegistrationService).registerProvider(request);
    }

    // DELETE PROVIDER - GOOD PATH
    @Test
    void deleteProvider_Deletes_WhenAuthorizedAndExists(){
        Long id = 1L;
        ProviderProfile mockProfile = Mockito.mock(ProviderProfile.class);

        when(mockProfile.getUserId()).thenReturn(1L);
        when(providerProfileMapper.getByProviderId(id)).thenReturn(mockProfile);
        when(providerProfileMapper.deleteProviderProfileById(id)).thenReturn(1);

        try(MockedStatic<AuthorizationService> auth = Mockito.mockStatic(AuthorizationService.class)){
            auth.when(()-> AuthorizationService.authorizeCurrentUserOrAdmin(eq(id),anyString()))
                    .thenAnswer(invocationOnMock -> null);
            assertDoesNotThrow(()-> providerProfileService.deleteProviderProfileById(id));
            verify(providerProfileMapper).deleteProviderProfileById(id);
        }
    }
}
