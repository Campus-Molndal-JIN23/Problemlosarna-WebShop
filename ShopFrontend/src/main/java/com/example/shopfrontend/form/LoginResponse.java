package com.example.shopfrontend.form;

import lombok.Data;

/**
 * class to hold login response data
 */
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
