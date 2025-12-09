package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingDTOMapper;
import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;
import com.example.provy.providerOffering.exception.ProviderOfferingNotFoundException;
import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.providerProfile.ProviderProfileService;
import com.example.provy.security.AuthorizationService;
import com.example.provy.user.DTO.UserResponseDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ProviderOfferingServiceImplTest {

    @Mock
    private ProviderOfferingMapper providerOfferingMapper;
    @Mock
    private ProviderProfileService providerProfileService;
    @Mock
    private ProviderOfferingDTOMapper providerOfferingDTOMapper;
    @Mock
    private ProviderOfferingFactory providerOfferingFactory;
    private ProviderOfferingServiceImpl providerOfferingService;

    @BeforeEach
    void setup(){
        providerOfferingService = new ProviderOfferingServiceImpl(providerOfferingMapper,providerProfileService,providerOfferingDTOMapper,providerOfferingFactory);
    }

    // GET OFFERING BY ID - GOOD PATH
    @Test
    void getById_ReturnsDTO(){
        Long id = 1L;
        ProviderOffering offering = Mockito.mock(ProviderOffering.class);
        ProviderOfferingResponseDTO dto = new ProviderOfferingResponseDTO();

        when(providerOfferingMapper.getById(id)).thenReturn(offering);
        when(providerOfferingDTOMapper.toResponseDTO(offering)).thenReturn(dto);

        ProviderOfferingResponseDTO result = providerOfferingService.getById(id);
        assertEquals(dto, result);
    }

    //GET OFFERING BY ID - OFFERING NOT FOUND
    @Test
    void getById_ThrowsException_WhenOfferingDoesNotExists(){
        Long id = 1L;

        when(providerOfferingMapper.getById(id)).thenReturn(null);
        assertThrows(ProviderOfferingNotFoundException.class, () -> providerOfferingService.getById(id));
        verifyNoInteractions(providerOfferingDTOMapper);
    }

    // REGISTER OFFERING - DELEGATES
    @Test
    void registerOffering_DelegatesToFactory(){
        ProviderOffering offering = Mockito.mock(ProviderOffering.class);
        ProviderOfferingRequestDTO dto = Mockito.mock(ProviderOfferingRequestDTO.class);

        when(providerOfferingFactory.create(dto)).thenReturn(offering);
        providerOfferingService.registerProviderOffering(dto);

        verify(providerOfferingFactory).create(dto);
        verify(providerOfferingMapper).registerProviderOffering(offering);
    }

    // DELETE OFFERING - GOOD PATH
    @Test
    void deleteOffering_Deletes_WhenAuthorizedAndExists(){
        Long offeringId = 1L;
        Long providerId = 99L;

        ProviderOffering offering = Mockito.mock(ProviderOffering.class);
        ProviderProfileResponseDTO dtoResponse = Mockito.mock(ProviderProfileResponseDTO.class);
        UserResponseDTO user = Mockito.mock(UserResponseDTO.class);

        when(offering.getProviderProfileId()).thenReturn(1L);
        when(providerOfferingMapper.getById(offeringId)).thenReturn(offering);


        when(providerProfileService.getByProviderId(anyLong())).thenReturn(dtoResponse);
        when(dtoResponse.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(providerId);

        when(providerOfferingMapper.deleteProviderOffering(offeringId)).thenReturn(1);

        try(MockedStatic<AuthorizationService> auth = Mockito.mockStatic(AuthorizationService.class)){
            auth.when(()-> AuthorizationService.authorizeCurrentUserOrAdmin(eq(providerId),anyString()))
                    .thenAnswer(invocationOnMock -> null);

            assertDoesNotThrow(()-> providerOfferingService.deleteProviderOffering(offeringId));

            auth.verify(()-> AuthorizationService.authorizeCurrentUserOrAdmin(providerId, "You do not have permission to delete this provider offering."));

            verify(providerOfferingMapper).deleteProviderOffering(offeringId);
        }
    }
}
