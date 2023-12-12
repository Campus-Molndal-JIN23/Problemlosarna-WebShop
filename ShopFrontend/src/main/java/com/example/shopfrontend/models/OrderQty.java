package com.example.shopfrontend.models;

import lombok.*;

@Data
public class OrderQty {

    private long id;
    private Product product;
    private Integer quantity;

    private Order order;

    public OrderQty(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderQty(Product product, Integer quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

}
