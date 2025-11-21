package com.example.provy.Common.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context){
        if(password == null) return false;

        boolean longEnough = password.length() >= 6;
        boolean containsDigit = password.matches(".*\\d.*");

        return longEnough && containsDigit;
    }
}
