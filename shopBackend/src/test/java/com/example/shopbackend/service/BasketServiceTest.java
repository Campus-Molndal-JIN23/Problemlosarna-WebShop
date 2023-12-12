package com.example.shopbackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class BasketServiceTest {

    @Autowired
    private BasketService basketService;


    @Test
    void getAExistingBasket() {
        var basket = basketService.getBasket(1L);
        assertNotNull(basket);
    }

    @Test
    void tryGetABasketThatDontExist() {
        var basket = basketService.getBasket(998957943L);
        assertNull(basket);
    }
}