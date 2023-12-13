package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderQtyRepository orderQtyRepository;

    public OrderService(OrderRepository orderRepository, OrderQtyRepository orderQtyRepository) {
        this.orderRepository = orderRepository;
        this.orderQtyRepository = orderQtyRepository;
    }

    public OrderDTO findAllUserOrders(Long userId) {
        List<List<OrderQty>> baskets = new ArrayList<>();

        // orders are always past baskets and set to false
        List<Order> orders = orderRepository.getByUserIdAndActiveBasket(userId, false).orElse(null);

        if (orders == null) {
            return null;
        } else {
            for (Order order : orders) {
                baskets.add(orderQtyRepository.findOrderQtyByOrderId(order.getId()));
            }
            return new OrderDTO(orders, baskets);
        }
    }

}
