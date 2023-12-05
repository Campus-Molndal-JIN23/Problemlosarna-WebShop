package com.example.shopbackend.entity;

import java.util.HashMap;

public class Basket {

    private int totatCost;

    private HashMap<Product, Integer> products;

    public Basket(int totatCost, HashMap<Product, Integer> products) {
        this.totatCost = totatCost;
        this.products = products;
    }
}
