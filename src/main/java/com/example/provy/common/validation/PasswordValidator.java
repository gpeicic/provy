package com.example.provy.common.validation;

import com.example.provy.utils.PasswordUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context){
        return PasswordUtil.isValid(password);
    }
}
