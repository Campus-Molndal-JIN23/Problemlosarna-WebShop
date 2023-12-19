package com.example.shopfrontend.models;

import lombok.Data;

@Data
public class Authority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority() {
    }
}
