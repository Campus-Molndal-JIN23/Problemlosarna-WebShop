package com.example.shopbackend.entity;

@Deprecated
public record ProductDTO(

        ProductOld product,
        int quantity

) {
}
