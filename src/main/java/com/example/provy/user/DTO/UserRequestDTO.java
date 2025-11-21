package com.example.provy.user.DTO;

import com.example.provy.common.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserRequestDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    @ValidPassword
    private String password;
    @NotNull
    private String ime;
    @NotNull
    private String prezime;

    public UserRequestDTO(){
    }

    public UserRequestDTO(String email, String password, String ime, String prezime) {
        this.email = email;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
