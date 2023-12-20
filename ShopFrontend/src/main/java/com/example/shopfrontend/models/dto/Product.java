package com.example.shopfrontend.models.dto;

import lombok.*;


@Data
public class Product {

    private long id;
    private String name;
    private int price;
    private String description;
    private boolean deleted;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
