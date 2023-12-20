package com.example.shopfrontend.models.userDetails;

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
