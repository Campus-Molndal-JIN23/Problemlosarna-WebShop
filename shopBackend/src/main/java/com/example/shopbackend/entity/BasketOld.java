package com.example.shopbackend.entity;

import java.util.HashMap;

public class BasketOld {

    private int totalCost;

    private HashMap<ProductOld, Integer> products;

    public BasketOld(int totalCost, HashMap<ProductOld, Integer> products) {
        this.totalCost = totalCost;
        this.products = products;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public HashMap<ProductOld, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<ProductOld, Integer> products) {
        this.products = products;
    }
}
