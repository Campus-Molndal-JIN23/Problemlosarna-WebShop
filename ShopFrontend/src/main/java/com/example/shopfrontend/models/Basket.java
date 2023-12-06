package com.example.shopfrontend.models;

import lombok.Data;

import java.util.HashMap;

@Data
public class Basket {

    private int totalCost;

    private HashMap<Product, Integer> products;
}
