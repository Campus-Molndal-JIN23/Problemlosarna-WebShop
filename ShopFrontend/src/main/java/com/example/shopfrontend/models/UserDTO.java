package com.example.shopfrontend.models;

import java.util.List;

public class UserDTO {
    private String username;
    private boolean authenticated;
    private List<String> authorities;
    private String remoteAddress;
    private String sessionId;
}
