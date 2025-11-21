package com.example.provy.providerOffering.exception;

public class ProviderOfferingNotFoundException extends RuntimeException {

    public ProviderOfferingNotFoundException(){
        super("Provider offering not found.");
    }
}
