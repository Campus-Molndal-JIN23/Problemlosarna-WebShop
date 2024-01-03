package com.example.shopfrontend.models.dto;

import lombok.*;

@Data
public class OrderQty {

    private long id;
    private ProductDTO product;
    private Integer quantity;

    private Order order;

    public OrderQty(ProductDTO product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderQty(ProductDTO product, Integer quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderQty() {

    }
}
