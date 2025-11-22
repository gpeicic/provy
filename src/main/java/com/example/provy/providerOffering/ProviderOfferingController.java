package com.example.provy.providerOffering;

import com.example.provy.providerOffering.DTO.ProviderOfferingRequestDTO;
import com.example.provy.providerOffering.DTO.ProviderOfferingResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providerOffering")
public class ProviderOfferingController {

    private final ProviderOfferingService providerOfferingService;

    public ProviderOfferingController(ProviderOfferingService providerOfferingService){
        this.providerOfferingService = providerOfferingService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<ProviderOfferingResponseDTO> getById(@PathVariable Long id){
        ProviderOfferingResponseDTO providerOffering = providerOfferingService.getById(id);

        return ResponseEntity.ok(providerOffering);
    }

    @GetMapping("/providerProfile/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<ProviderOfferingResponseDTO> getByProviderProfileId(@PathVariable Long id){
        ProviderOfferingResponseDTO providerOffering = providerOfferingService.getByProviderProfileId(id);

        return ResponseEntity.ok(providerOffering);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PROVIDER','ADMIN')")
    public ResponseEntity<Void> registerProviderOffering(@Valid @RequestBody ProviderOfferingRequestDTO providerOffering){
        providerOfferingService.registerProviderOffering(providerOffering);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROVIDER','ADMIN')")
    public ResponseEntity<Void> deleteProviderOffering(@PathVariable Long id){
        providerOfferingService.deleteProviderOffering(id);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}
