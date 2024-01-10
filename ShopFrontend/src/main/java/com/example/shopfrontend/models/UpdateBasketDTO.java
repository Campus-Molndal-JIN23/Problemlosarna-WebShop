package com.example.shopfrontend.models;

import lombok.Data;

@Data
public class UpdateBasketDTO {
    private Long productId;
    private int quantity;

}