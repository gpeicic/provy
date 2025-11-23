package com.example.provy.admin;

import com.example.provy.providerProfile.DTO.ProviderProfileResponseDTO;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserResponseDTO> getByUserId(@PathVariable Long id){
        UserResponseDTO user = adminService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @GetMapping("/providers")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<ProviderProfileResponseDTO>> getAllProviders(){
        List<ProviderProfileResponseDTO> providers = adminService.getAllProviderProfiles();
        return ResponseEntity.status(HttpStatus.OK).body(providers);
    }
    @GetMapping("/providers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ProviderProfileResponseDTO> getByProviderId(@PathVariable Long id){
        ProviderProfileResponseDTO provider = adminService.getProviderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(provider);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> createAdmin(@RequestBody UserRequestDTO dto){
        adminService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/providers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteProviderById(@PathVariable Long id){
        adminService.deleteProvider(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
