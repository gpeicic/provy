package com.example.provy.ProviderProfile;

import com.example.provy.ProviderProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.ProviderProfile.DTO.ProviderRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
public class ProviderProfileController {

    private final ProviderProfileService providerProfileService;


    public ProviderProfileController(ProviderProfileService providerProfileService){
        this.providerProfileService = providerProfileService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderProfileResponseDTO> getByProviderId(@PathVariable Long id){
       ProviderProfileResponseDTO responseDTO = providerProfileService.getByProviderId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<Void> registerProviderProfile(@Valid @RequestBody ProviderRegistrationRequest provider){
        providerProfileService.registerProviderProfile(provider);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProviderProfileById(@PathVariable Long id){
        providerProfileService.deleteProviderProfileById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
