package com.example.shopbackend.model;

import com.example.shopbackend.entity.OrderQty;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BasketDTO {


    private int totalCost;
    private List<BasketProductDTO> products;

    public BasketDTO(List<OrderQty> products) {
        int sum = 0;
        this.products = new ArrayList<>();

        for (OrderQty ordQty : products) {
            sum += ordQty.getProduct().getPrice() * ordQty.getQuantity();
            this.products.add(new BasketProductDTO(ordQty));
        }
        this.totalCost = sum;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
                "totalCost=" + totalCost +
                ", products=" + products +
                '}';
    }
}
