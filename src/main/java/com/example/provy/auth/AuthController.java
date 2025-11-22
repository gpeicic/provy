package com.example.provy.auth;

import com.example.provy.auth.DTO.AuthRequestDTO;
import com.example.provy.auth.DTO.AuthResponseDTO;
import com.example.provy.providerProfile.DTO.ProviderRegistrationRequest;
import com.example.provy.providerProfile.ProviderProfile;
import com.example.provy.providerProfile.ProviderProfileService;
import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.User;
import com.example.provy.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final ProviderProfileService providerProfileService;

    public AuthController(AuthService authService, UserService userService,ProviderProfileService providerProfileService) {
        this.authService = authService;
        this.userService = userService;
        this.providerProfileService = providerProfileService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO requestDTO){
        String token = authService.authenticate(requestDTO.getEmail(),requestDTO.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/registerUser")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody UserRequestDTO requestDTO){
        User user = userService.registerUser(requestDTO);
        String token = authService.generateTokenForUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
    }
    @PostMapping("/registerProvider")
    public ResponseEntity<AuthResponseDTO> registerProvider(@RequestBody ProviderRegistrationRequest requestDTO){
        ProviderProfile profile = providerProfileService.registerProviderProfile(requestDTO);
        User user = userService.getUserByEmail(requestDTO.getUser().getEmail());

        String token = authService.generateTokenForUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
    }
}
