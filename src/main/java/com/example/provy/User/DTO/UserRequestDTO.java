package com.example.provy.User.DTO;

import org.springframework.lang.NonNull;

public class UserRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String ime;
    @NonNull
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
