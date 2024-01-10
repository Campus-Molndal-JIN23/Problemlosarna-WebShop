package com.example.shopfrontend.models.dto;

import lombok.Data;

@Data
public class Order {

    private long id;
    private BasketDTO Basket;
}
