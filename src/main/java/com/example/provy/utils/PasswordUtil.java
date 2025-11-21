package com.example.provy.utils;

public class PasswordUtil {
    public static boolean isLongEnough(String password){
        return password != null && password.length() >= 6;
    }

    public static boolean hasDigit(String password){
        return password != null && password.matches(".*\\d.*");
    }

    public static boolean hasUppercase(String password){
        return password != null && password.matches(".*[A-Z].*");
    }

    public static boolean isValid(String password){
        if(password == null) return false;

        return isLongEnough(password) &&
                hasDigit(password) &&
                hasUppercase(password);
    }
}
