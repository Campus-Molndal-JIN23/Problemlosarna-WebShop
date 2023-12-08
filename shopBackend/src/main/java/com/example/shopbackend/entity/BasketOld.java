package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class BasketOld {

    private int totalCost;

    private HashMap<ProductOld, Integer> products;

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