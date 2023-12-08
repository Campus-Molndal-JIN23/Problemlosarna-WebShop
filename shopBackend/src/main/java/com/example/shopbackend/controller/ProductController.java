package com.example.shopbackend.controller;


import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.ProductOld;
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
        log.info("create Product: " + product);
        var savedProduct = productService.save(product);

        if (savedProduct instanceof Product) {
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOld> updateOne(@PathVariable long id, @RequestBody ProductOld product) {
        log.info("update Product: " + product);

        return ResponseEntity.ok(new ProductOld(1, "A updated product", 42, "Not the product you sent but a generic return"));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable long id) {
        log.info("delete Product: ");
        return ResponseEntity.noContent().build();
    }

}
