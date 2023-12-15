package com.example.shopbackend.controller;

import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/webshop")
public class OrderController {

    private final OrderService orderService;



    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping ("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> Order() {
        OrderDTO order = orderService.placeOrder(1L);

        return order== null ? ResponseEntity.notFound().build():ResponseEntity.ok(order);

    }


    @GetMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> getOrder() {


        OrderDTO orders = orderService.findAllUserOrders(1L);

        return orders == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(orders);
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
