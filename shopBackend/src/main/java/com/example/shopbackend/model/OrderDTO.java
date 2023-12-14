package com.example.shopbackend.model;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class OrderDTO {


    private final String username;
    private final List<BasketDTO> orders;

    public OrderDTO(List<Order> user, List<List<OrderQty>> baskets) {
        this.username = user.get(0).getUser().getUserName();//Todo user.getFirst().getUser().getUserName()

        this.orders = new ArrayList<>();

        for (int i = 0; i < baskets.size(); i++) {
            orders.add(new BasketDTO(baskets.get(i)));
        }
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orders=" + orders +
                '}';
    }
}
