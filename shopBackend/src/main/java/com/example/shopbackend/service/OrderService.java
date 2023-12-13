package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderQtyRepository orderQtyRepository;

    public OrderService(OrderRepository orderRepository, OrderQtyRepository orderQtyRepository) {
        this.orderRepository = orderRepository;
        this.orderQtyRepository = orderQtyRepository;
    }

    public List<Object> findAllUserOrders(Long userId) {

        Optional<List<Order>> orders = orderRepository.findByUserId(userId);
// todo continue here
        OrderDTO userOrders = new OrderDTO();

        return null;
    }

}
