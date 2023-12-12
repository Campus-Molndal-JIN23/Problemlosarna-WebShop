package com.example.shopbackend.model;

import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.Product;

import java.util.List;

public class BasketProductDTO {

    private int quantity;
    private String name;
    private String description;
    private int price;

    public BasketProductDTO(OrderQty item) {
        this.quantity = item.getQuantity();
        this.name = item.getProduct().getName();
        this.description = item.getProduct().getDescription();
        this.price = item.getProduct().getPrice();
    }

    @Override
    public String toString() {
        return "BasketProductDTO{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
