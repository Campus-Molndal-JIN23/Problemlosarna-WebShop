package com.example.shopbackend.controller;

import org.apache.catalina.User;
import com.example.shopbackend.entity.Basket;
import com.example.shopbackend.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/webshop/basket")
public class BasketController {

    @GetMapping
    public ResponseEntity<Basket> getBasket(@RequestBody User user) {

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);
        products.put(new Product("added Product 4", 400, " not realy added but you get the idea"), 1);

        var basket = new Basket(1000, products);

        return ResponseEntity.ok(basket);
    }

    @PostMapping("/{id}") // this is a product id
    public ResponseEntity<Basket> addProductToBasket(@PathVariable int id) {
        // TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);
        products.put(new Product("added Product 4", 400, " not realy added but you get the idea"), 1);

        var basket = new Basket(1000, products);

        return ResponseEntity.ok(basket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateQuantity(@PathVariable int id, @RequestBody HashMap<Product, Integer> updatedQty) {
// TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);
        products.put(new Product("added Product 4", 400, " not realy added but you get the idea"), 1);

        var basket = new Basket(1000, products);

        return ResponseEntity.ok(basket);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }


}
