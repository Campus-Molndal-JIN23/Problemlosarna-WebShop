package com.example.shopbackend.model;

import com.example.shopbackend.entity.OrderQty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailsDTO {


    private final List<OrderDTO> orders;




}
