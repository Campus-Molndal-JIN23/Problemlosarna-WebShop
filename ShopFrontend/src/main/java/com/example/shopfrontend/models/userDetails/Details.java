package com.example.shopfrontend.models.userDetails;

import lombok.Data;

@Data
public class Details {

    private String remoteAddress;
    private String sessionId;

    public Details(String remoteAddress, String sessionId) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
    }

    public Details() {
    }
}
