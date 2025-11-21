package com.example.provy.ProviderProfile.Exception;

public class ProviderNotFoundException extends RuntimeException{

    public ProviderNotFoundException(Long id){
        super("Provider with id " + id + " not found.");
    }
}
