package com.example.shopbackend.controller;

import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.security.ExtractData;
import com.example.shopbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/webshop")
public class OrderController {

    private final OrderService orderService;
    private final ExtractData extractData;


    public OrderController(OrderService orderService, ExtractData extractData) {
        this.orderService = orderService;
        this.extractData = extractData;
    }

    @PostMapping ("/order")
    public ResponseEntity<Object> Order(@RequestBody String jwt) {

        long userid = extractData.getUserID(jwt);
        OrderDTO order = orderService.placeOrder(userid);

        return order== null ? ResponseEntity.notFound().build():ResponseEntity.ok(order);

    }


    @GetMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> getOrder(@RequestBody String jwt) {

        long userid = extractData.getUserID(jwt);
        OrderDTO orders = orderService.findAllUserOrders(userid);

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
