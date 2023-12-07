package com.example.shopbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;



public class ProductOld {


    private long id;
    private String name;

    private int cost;

    private String description;


    public ProductOld(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public ProductOld(long id, String name, int cost, String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public ProductOld() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                '}';
    }
}
