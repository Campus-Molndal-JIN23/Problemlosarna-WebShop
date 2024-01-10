package com.example.shopfrontend.models.dto;


import lombok.Data;

@Data
public class BasketProductDTO {
    private long id;
    private int quantity;
    private String name;
    private String description;
    private int price;

    public BasketProductDTO(OrderQty item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.name = item.getProduct().getName();
        this.description = item.getProduct().getDescription();
        this.price = item.getProduct().getPrice();
    }

    public BasketProductDTO() {
    }

    public BasketProductDTO(long id, int quantity, String name, String description, int price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
