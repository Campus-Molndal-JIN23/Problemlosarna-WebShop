package com.example.shopbackend.controller;


import com.example.shopbackend.entity.*;
import com.example.shopbackend.form.UserTest;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webshop")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> Order(@RequestBody User user) {

        HashMap<ProductOld, Integer> products = new HashMap<>();

        products.put(new ProductOld("Product 1", 100, "Text about the product 1"), 1);
        products.put(new ProductOld("Product 2", 200, "Text about the product 2"), 2);

        BasketOld basket = new BasketOld(100, products);


        return ResponseEntity.ok(new OrderOld(1, basket));

    }


    @GetMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> getOrder() {

        OrderDTO orders = orderService.findAllUserOrders(1L);

        return orders == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(orders);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getOrders() {


        OrderDTO orders = orderService.findAllOrders();
        HashMap<String, ArrayList<UserTest>> users = new HashMap<>();
        users.put("users", getUserData());

        return ResponseEntity.ok(orders);
    }

    private ArrayList<UserTest> getUserData() {
        ArrayList<UserTest> users = new ArrayList<>();

        UserTest userTest = new UserTest("user1", orders());
        UserTest userTest2 = new UserTest("user2", orders());

        users.add(userTest);
        users.add(userTest2);
        return users;
    }

    private ArrayList<OrderOld> orders() {
        HashMap<ProductOld, Integer> products = new HashMap<>();

        products.put(new ProductOld("Product 1", 100, "Text about the product 1"), 1);
        products.put(new ProductOld("Product 2", 200, "Text about the product 2"), 2);
        products.put(new ProductOld("Product 3", 300, "Text about the product 3"), 3);
        products.put(new ProductOld("Product 4", 400, "Text about the product 4"), 4);

        BasketOld basket = new BasketOld(1000, products);

        ArrayList<OrderOld> orders = new ArrayList<>();
        orders.add(new OrderOld(1, basket));
        orders.add(new OrderOld(2, basket));

        return orders;
    }

}
