package com.example.shopbackend.entity;

import java.util.HashMap;

public class Basket {

    private int totalCost;

    private HashMap<Product, Integer> products;

    public Basket(int totalCost, HashMap<Product, Integer> products) {
        this.totalCost = totalCost;
        this.products = products;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }
}
