package com.example.provy.user.DTO;

import com.example.provy.role.Role;

import java.util.HashSet;
import java.util.Set;

public class UserResponseDTO{
    private Long id;
    private String email;
    private String ime;
    private String prezime;
    private Set<Role> roles = new HashSet<>();

   public UserResponseDTO(){}
    public UserResponseDTO(Long id, String email, String ime, String prezime, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
