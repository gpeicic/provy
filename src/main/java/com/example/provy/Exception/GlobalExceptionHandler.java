package com.example.provy.Exception;

import com.example.provy.ProviderOffering.Exception.ProviderOfferingAlreadyExistsException;
import com.example.provy.ProviderOffering.Exception.ProviderOfferingNotFoundException;
import com.example.provy.ProviderProfile.Exception.InvalidWorkingHoursException;
import com.example.provy.ProviderProfile.Exception.ProviderAlreadyExists;
import com.example.provy.ProviderProfile.Exception.ProviderNotFoundException;
import com.example.provy.User.Exception.RoleNotFoundException;
import com.example.provy.User.Exception.UserAlreadyExistsAException;
import com.example.provy.User.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //PROVIDER_OFFERING
    @ExceptionHandler(ProviderOfferingNotFoundException.class)
    public ResponseEntity<String> handleProviderOfferingNotFound(ProviderOfferingNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(ProviderOfferingAlreadyExistsException.class)
    public ResponseEntity<String> handleProviderOfferingAlreadyExists(ProviderOfferingAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    //PROVIDER_PROFILE
     @ExceptionHandler(InvalidWorkingHoursException.class)
     public ResponseEntity<String> handleInvalidWorkingHours(InvalidWorkingHoursException e){
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
    @ExceptionHandler(ProviderAlreadyExists.class)
    public ResponseEntity<String> handleProviderAlreadyExists(ProviderAlreadyExists e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFound(ProviderNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    //USER
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFound(RoleNotFoundException e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistsAException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsAException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException e){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Neispravan format podataka ili tip polja u JSON-u");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
