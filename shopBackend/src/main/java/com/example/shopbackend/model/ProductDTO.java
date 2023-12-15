package com.example.shopbackend.model;

import com.example.shopbackend.entity.Product;

public record ProductDTO(

        Long id,
        String name,
        String description,
        Integer price
) {
    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}




