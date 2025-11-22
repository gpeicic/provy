package com.example.provy.auth;

import com.example.provy.auth.DTO.AuthRequestDTO;
import com.example.provy.auth.DTO.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO requestDTO){
        String token = authService.authenticate(requestDTO.getEmail(),requestDTO.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
