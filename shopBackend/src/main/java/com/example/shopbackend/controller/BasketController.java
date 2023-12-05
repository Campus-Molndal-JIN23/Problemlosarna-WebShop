package com.example.shopbackend.controller;


import com.example.shopbackend.entity.Basket;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/webshop/basket")
public class BasketController {


    @GetMapping("")
    public ResponseEntity<Basket> getBasket() {

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);


        var basket = new Basket(600, products);

        return ResponseEntity.ok(basket);
    }


    @PostMapping("") // this is a product id
    public ResponseEntity<Basket> addProductToBasket(@RequestBody ProductDTO payload) {

        // TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);
        products.put(new Product("added Product 4", 400, " not realy added but you get the idea"), 1);
        products.put(payload.product(), payload.quantity());

        var basket = new Basket(80085, products);

        return ResponseEntity.ok(basket);
    }

    @PutMapping("")
    public ResponseEntity<Basket> updateQuantity(@RequestBody ProductDTO payload) {
// TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<Product, Integer> products = new HashMap<>();

        products.put(new Product("Product 1", 100, "Text about the product 1"), 2);
        products.put(new Product("Product 2", 200, "Text about the product 2"), 5);
        products.put(new Product("Product 3", 300, "Text about the product 3"), 1);
        products.put(new Product("added Product 4", 400, " not really added but you get the idea"), 1);
        products.put(payload.product(), payload.quantity());

        var basket = new Basket(80085, products);

        return ResponseEntity.ok(basket);
    }


    @DeleteMapping("")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product) {
        return ResponseEntity.noContent().build();
    }


}
