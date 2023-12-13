package com.example.shopbackend.form;

public record UpdateBasketDTO(
        Long productId,
        int quantity
) {
}
