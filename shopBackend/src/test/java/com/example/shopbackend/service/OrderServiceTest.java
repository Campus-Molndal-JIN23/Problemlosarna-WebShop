package com.example.shopbackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void findAllUserOrders() {

        var orders = orderService.findAllUserOrders(1L);
        System.out.println(orders.toString());

    }
}