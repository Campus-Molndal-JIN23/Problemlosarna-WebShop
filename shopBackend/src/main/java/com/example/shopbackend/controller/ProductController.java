package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/webshop/products")
public class ProductController {


    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {

        List<Product> products = new ArrayList<>();

        products.add(new Product(1,"Product 1", 100, "Text about the product 1"));
        products.add(new Product(2,"Product 2", 200, "Text about the product 2"));
        products.add(new Product(3,"Product 3", 300, "Text about the product 3"));
        products.add(new Product(4,"Product 4", 400, "Text about the product 4"));


        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable long id) {

        return ResponseEntity.ok(new Product(1,"Product 1", 100, "Text about the product 1"));
    }

    @PostMapping("")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {
        log.info("create Product: " + product);
        return ResponseEntity.ok(new Product(1,"A created product", 42, "Not the product you sent but a generic return"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateOne(@PathVariable long id, @RequestBody Product product) {
        log.info("update Product: " + product);

        return ResponseEntity.ok(new Product(1,"A updated product", 42, "Not the product you sent but a generic return"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable long id) {
        log.info("delete Product: ");
        return ResponseEntity.noContent().build();
    }

}
