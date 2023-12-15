package com.example.shopbackend.controller;


import com.example.shopbackend.entity.Product;
import com.example.shopbackend.model.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAll() {

        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable long id) {

        Product product = productService.findById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createOne(@RequestBody ProductDTO product) {

        ProductDTO savedProduct = productService.save(product);

        if (savedProduct != null) {
            return ResponseEntity.ok(savedProduct);
        } else { // How to write a test for this condition?
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<ProductDTO> updateOne(@RequestBody ProductDTO product) {
        var response = productService.update(product);
        if (response != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteOne(@RequestBody ProductDTO product) {

        if (productService.delete(product.id())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
