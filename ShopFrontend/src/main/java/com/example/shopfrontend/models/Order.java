package com.example.shopfrontend.models;

import lombok.Data;

@Data
public class Order {

    private Basket basket;

    private int totalcost;
}
