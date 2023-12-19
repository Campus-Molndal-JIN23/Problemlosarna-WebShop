package com.example.shopfrontend.models;

import lombok.Data;


import java.util.List;


@Data
public class UserDTO {
    private String name;
    private boolean authenticated;
    private Principal principal;
    private Details details;
    private String password;
    private String credentials;
    private List<Authority> authorities;
}
