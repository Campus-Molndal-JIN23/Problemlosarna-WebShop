package com.example.shopbackend.controller;


import com.example.shopbackend.entity.Basket;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.form.UserTest;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/webshop")
public class OrderController {


    @PostMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> Order(@RequestBody User user){

        HashMap<Product,Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 1);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 2);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 3);
        products.put(new Product("Product 4", 400, "Text about the product 4"), 4);

        Basket basket = new Basket(100,products);


        return ResponseEntity.ok(new Order(1,basket));

    }


    @GetMapping("/order")             //TODO Check om vi ska använda userDTO
    public ResponseEntity<Object> getOrder(@RequestBody User user){

        return ResponseEntity.ok(orders());

    }

    @GetMapping("/orders")
    public ResponseEntity<Object>getOrders(){
        ArrayList<UserTest> users = new ArrayList<>();

        UserTest userTest = new UserTest("user1",orders());
        UserTest userTest2 = new UserTest("user2",orders());

        users.add(userTest);
        users.add(userTest2);

        return ResponseEntity.ok(users);
    }

    private ArrayList<Order> orders(){
        HashMap<Product,Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 1);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 2);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 3);
        products.put(new Product("Product 4", 400, "Text about the product 4"), 4);

        Basket basket = new Basket(1000,products);

        ArrayList<Order>orders= new ArrayList<>();
        orders.add(new Order(1,basket));
        orders.add(new Order(2,basket));

        return  orders;
    }

}
