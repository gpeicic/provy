package com.example.provy.user;

import com.example.provy.user.DTO.UserRequestDTO;
import com.example.provy.user.DTO.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRequestDTO user){
        userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','PROVIDER','ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
