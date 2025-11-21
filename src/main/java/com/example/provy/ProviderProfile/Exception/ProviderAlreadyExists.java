package com.example.provy.ProviderProfile.Exception;

public class ProviderAlreadyExists extends RuntimeException{

    public ProviderAlreadyExists(String businessName){
        super("Provider with name " + businessName + " already exists.");
    }
}
