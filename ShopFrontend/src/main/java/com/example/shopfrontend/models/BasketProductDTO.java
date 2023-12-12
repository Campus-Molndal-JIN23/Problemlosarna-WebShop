package com.example.shopfrontend.models;


import lombok.Data;
import lombok.Getter;

@Data
public class BasketProductDTO {
    private long id;
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

}
