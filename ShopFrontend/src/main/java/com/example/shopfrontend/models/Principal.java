package com.example.shopfrontend.models;

import lombok.Data;

import java.util.List;

@Data
public class Principal {
    private String password;
    private String username;
    private List<Authority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public Principal(String password
            , String username
            , List<Authority> authorities
            , boolean accountNonExpired
            , boolean accountNonLocked
            , boolean credentialsNonExpired
            , boolean enabled) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public Principal() {
    }
}
