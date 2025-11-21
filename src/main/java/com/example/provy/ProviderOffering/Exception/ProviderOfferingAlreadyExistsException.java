package com.example.provy.ProviderOffering.Exception;

public class ProviderOfferingAlreadyExistsException extends RuntimeException{
    public ProviderOfferingAlreadyExistsException(String name){
        super("Provider offering with name " + name + " already exists.");
    }
}
