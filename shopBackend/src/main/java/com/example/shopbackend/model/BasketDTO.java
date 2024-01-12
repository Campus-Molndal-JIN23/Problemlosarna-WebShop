package com.example.shopbackend.model;

import com.example.shopbackend.entity.OrderQty;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BasketDTO {

    private final Long basketId;
    private final Long totalCost;
    private final List<BasketProductDTO> products;

    public BasketDTO(List<OrderQty> products) {
        int sum = 0;
        this.products = new ArrayList<>();

        if (!products.isEmpty()) {
            for (OrderQty ordQty : products) {
                sum += ordQty.getProduct().getPrice() * ordQty.getQuantity();
                this.products.add(new BasketProductDTO(ordQty));
            }
            this.basketId = products.getFirst().getOrder().getId();
        } else {
            this.basketId = null; // or set it to an appropriate default value
        }

        this.totalCost = (long) sum;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
                "totalCost=" + totalCost +
                ", products=" + products +
                '}';
    }
}
