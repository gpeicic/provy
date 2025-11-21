package com.example.provy.User.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("User with id "+ id + " not found.");
    }
}
