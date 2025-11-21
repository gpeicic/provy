package com.example.provy.ProviderOffering.Exception;

import com.example.provy.ProviderOffering.ProviderOffering;

public class ProviderOfferingNotFoundException extends RuntimeException {

    public ProviderOfferingNotFoundException(){
        super("Provider offering not found.");
    }
}
