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
        this.username = user.getFirst().getUser().getUsername();

        this.orders = new ArrayList<>();

        for (List<OrderQty> basket : baskets) {
            orders.add(new BasketDTO(basket));
        }
    }

    public OrderDTO(Order user, List<OrderQty> baskets) {
        this.username = user.getUser().getUsername();

        this.orders = new ArrayList<>();

            orders.add(new BasketDTO(baskets));
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orders=" + orders +
                '}';
    }
}
