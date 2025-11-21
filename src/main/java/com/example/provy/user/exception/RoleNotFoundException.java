package com.example.provy.user.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String role){
        super("Role " + role + " not found.");
    }
}
