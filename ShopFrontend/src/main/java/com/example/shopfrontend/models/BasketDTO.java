package com.example.shopfrontend.models;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Data
public class BasketDTO {

    private  Long basketId;
    private int totalCost;
    private List<BasketProductDTO> products;

}
