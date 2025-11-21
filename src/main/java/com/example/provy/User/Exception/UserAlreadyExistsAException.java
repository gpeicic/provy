package com.example.provy.User.Exception;

public class UserAlreadyExistsAException extends RuntimeException{
    public UserAlreadyExistsAException(String email){
        super("User with email " + email + " already exists.");
    }
}
