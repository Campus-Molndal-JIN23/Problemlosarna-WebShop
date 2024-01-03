package com.example.shopfrontend.models;

import com.example.shopfrontend.models.dto.BasketProductDTO;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Data
public class BasketDTO {

    private long basketId;
    private int totalCost;
    private List<BasketProductDTO> products;

    public BasketDTO() {
    }

    public BasketDTO(long basketId, int totalCost, List<BasketProductDTO> products) {
        this.basketId = basketId;
        this.totalCost = totalCost;
        this.products = products;
    }

}
