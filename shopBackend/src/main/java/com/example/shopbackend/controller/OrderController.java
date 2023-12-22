package com.example.shopbackend.controller;

import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.service.OrderService;
import com.example.shopbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/webshop")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;


    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/order")
    public ResponseEntity<Object> Order(Principal principal) {

        Long userid = userService.getUserId(principal);

        OrderDTO result = orderService.placeOrder(userid);
        log.error(result.toString());
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);

    }

    @GetMapping("/order")
    public ResponseEntity<Object> getOrder(Principal principal) {

        Long userid = userService.getUserId(principal);

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
