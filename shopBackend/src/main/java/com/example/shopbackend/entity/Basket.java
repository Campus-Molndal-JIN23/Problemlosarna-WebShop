package com.example.shopbackend.entity;

import java.util.HashMap;

public class Basket {

    private int totatCost;

    private HashMap<Product, Integer> products;

    public Basket(int totatCost, HashMap<Product, Integer> products) {
        this.totatCost = totatCost;
        this.products = products;
    }

    public int getTotatCost() {
        return totatCost;
    }

    public void setTotatCost(int totatCost) {
        this.totatCost = totatCost;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }
}
