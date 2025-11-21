package com.example.provy.user.exception;

public class UserAlreadyExistsAException extends RuntimeException{
    public UserAlreadyExistsAException(String email){
        super("User with email " + email + " already exists.");
    }
}
