package com.example.shopbackend.controller;


import com.example.shopbackend.entity.Product;
import com.example.shopbackend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/webshop/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {

        return ResponseEntity.ok(productService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable long id) {

        var product = productService.findById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {

        var savedProduct = productService.save(product);

        if (savedProduct != null) {
            return ResponseEntity.ok(savedProduct);
        } else { // How to write a test for this condition?
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<Product> updateOne(@RequestBody Product product) {
        var response = productService.update(product);
        if (response != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable long id) {

        if (productService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
