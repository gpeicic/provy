package com.example.provy.providerProfile.exception;

public class ProviderNotFoundException extends RuntimeException{

    public ProviderNotFoundException(Long id){
        super("Provider with id " + id + " not found.");
    }
}
