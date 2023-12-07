package com.example.shopfrontend.models;

import lombok.Data;

@Data
public class LoginResponse {

    private String username;
    private String token;
    private String role;


}
