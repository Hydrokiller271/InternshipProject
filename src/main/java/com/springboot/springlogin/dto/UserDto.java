package com.springboot.springlogin.dto;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.springboot.springlogin.model.Role;

@JsonFilter("userFilter")
public class UserDto {
    private long id;
    private String name;
    private String password;
    private String email;
    private boolean activate;
    private UUID token;
    private Set<Role> roles;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    

    public boolean isActivate() {
        return activate;
    }
    public void setActivate(boolean activate) {
        this.activate = activate;
    }
    public UUID getToken() {
        return token;
    }
    public void setToken(UUID token) {
        this.token = token;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public UserDto(){};
    public UserDto(String email,String password){
        this.email = email;
        this.password = password;
    }
    
}
