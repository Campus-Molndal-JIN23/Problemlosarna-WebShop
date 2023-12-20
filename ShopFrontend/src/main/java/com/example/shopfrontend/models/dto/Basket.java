package com.example.shopfrontend.models.dto;

import com.example.shopfrontend.models.dto.BasketProductDTO;
import lombok.Data;


import java.util.List;

@Data
public class Basket {

    private  int totalCost;
    private  List<BasketProductDTO> products;
}
