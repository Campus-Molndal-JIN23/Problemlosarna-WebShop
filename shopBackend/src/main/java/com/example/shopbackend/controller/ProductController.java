package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/webshop/products")
public class ProductController {


    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {

        List<Product> products = new ArrayList<>();

        products.add(new Product("Product 1", 100, "Text about the product 1"));
        products.add(new Product("Product 2", 200, "Text about the product 2"));
        products.add(new Product("Product 3", 300, "Text about the product 3"));
        products.add(new Product("Product 4", 400, "Text about the product 4"));


        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable long id) {

        return ResponseEntity.ok(new Product("A specific product", 42, "Text about the specific product"));
    }

    @PostMapping("")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {

        return ResponseEntity.ok(new Product("A created product", 42, "Not the product you sent but a generic return"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateOne(@RequestBody Product product) {


        return ResponseEntity.ok(new Product("A updated product", 42, "Not the product you sent but a generic return"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne() {

        return ResponseEntity.noContent().build();
    }

}
