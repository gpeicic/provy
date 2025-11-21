package com.example.provy.User.Exception;

import com.example.provy.Role.Role;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String role){
        super("Role " + role + " not found.");
    }
}
