package com.ecom.zestcart.dto;

import java.util.List;

public class UserDTO {
    private String token;
    private String userId;
    private String username;
    private List<String> roles;

    // Constructor
    public UserDTO(String token, String username, String userId, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    // Default constructor for deserialization
    public UserDTO() {}

    // Getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
