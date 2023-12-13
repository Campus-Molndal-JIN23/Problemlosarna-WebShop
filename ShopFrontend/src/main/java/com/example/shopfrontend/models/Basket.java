package com.example.shopfrontend.models;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Basket {

    private  int totalCost;
    private  List<BasketProductDTO> products;
}
