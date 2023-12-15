package com.example.shopbackend.controller;

import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.service.GetUser;
import com.example.shopbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/webshop")
public class OrderController {

    private final OrderService orderService;
    private final GetUser getUser;


    public OrderController(OrderService orderService, GetUser getUser) {
        this.orderService = orderService;
        this.getUser = getUser;
    }

    @PostMapping("/order")
    public ResponseEntity<Object> Order(Principal principal) {

        Long userid = getUser.getUserId(principal);

        OrderDTO result = orderService.placeOrder(userid);

        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);

    }


    @GetMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> getOrder(Principal principal) {

        Long userid = getUser.getUserId(principal);

        OrderDTO result = orderService.findAllUserOrders(userid);

        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getOrders() {
        try {

            OrderDetailsDTO orders = orderService.findAllOrders();
            if (orders.getAllOrders() == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
