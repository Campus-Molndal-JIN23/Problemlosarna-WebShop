package com.example.shopbackend.model;

import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.Product;
import lombok.Getter;

import java.util.List;
@Getter
public class BasketProductDTO {

    private final Long id;
    private final int quantity;
    private final String name;
    private final String description;
    private final int price;

    public BasketProductDTO(OrderQty item) {
        this.id = item.getId();
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
