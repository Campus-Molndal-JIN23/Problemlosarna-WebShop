package com.example.shopfrontend.models.dto;

import lombok.Data;

import java.util.List;


@Data
public class OrderDTO {

    String username;
    List<BasketDTO> orders;

    public OrderDTO() {
    }

    public OrderDTO(String username, List<BasketDTO> orders) {
        this.username = username;
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orders=" + orders +
                '}';
    }
}
