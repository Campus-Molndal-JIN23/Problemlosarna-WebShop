package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private static final Logger LOG = LoggerFactory.getLogger(BasketService.class);

    private final OrderRepository orderRepository;

    private final OrderQtyRepository orderQtyRepository;


    public BasketService(OrderRepository orderRepository, OrderQtyRepository orderQtyRepository) {
        this.orderRepository = orderRepository;
        this.orderQtyRepository = orderQtyRepository;
    }

    public BasketDTO getBasket(Long userId) {

        Optional<Order> order = orderRepository.findByUserIdAndActiveBasket(userId, true);

        return order.map(value -> new BasketDTO(orderQtyRepository.findOrderQtyByOrderId(value.getId()))).orElse(null);
    }


}
