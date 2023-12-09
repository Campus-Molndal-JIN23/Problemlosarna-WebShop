package com.example.shopfrontend.models;

import lombok.Data;

@Data
public class LoginResponse {

    private String username;
    private String token;
    private String role;


    public LoginResponse(String username, String token, String role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public LoginResponse() {
    }
}
