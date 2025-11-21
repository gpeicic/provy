package com.example.provy.providerProfile.exception;

public class ProviderAlreadyExists extends RuntimeException{

    public ProviderAlreadyExists(String businessName){
        super("Provider with name " + businessName + " already exists.");
    }
}
