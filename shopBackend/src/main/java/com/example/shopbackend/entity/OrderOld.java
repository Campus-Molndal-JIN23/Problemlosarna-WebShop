package com.example.shopbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Deprecated
public class OrderOld {


    long id;
    BasketOld basketOld;

}