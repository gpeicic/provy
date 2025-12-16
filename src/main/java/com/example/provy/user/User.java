package com.example.provy.user;

import com.example.provy.role.Role;

import java.util.HashSet;
import java.util.Set;


public class User {
    private Long id;
    private String email;
    private String password;
    private String ime;
    private String prezime;
    private String adresa;
    private Double latitude;
    private Double longitude;
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, String email, String password, String ime, String prezime, String adresa, Double latitude, Double longitude, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roles = roles;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
