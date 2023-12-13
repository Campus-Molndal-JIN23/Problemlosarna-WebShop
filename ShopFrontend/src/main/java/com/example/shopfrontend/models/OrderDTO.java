package com.example.shopfrontend.models;

import lombok.Data;

import java.util.List;


@Data
public class OrderDTO {

    private final String username;
    private final List<BasketDTO> orders;


    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orders=" + orders +
                '}';
    }
}
