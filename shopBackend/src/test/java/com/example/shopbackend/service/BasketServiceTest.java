package com.example.shopbackend.service;

import com.example.shopbackend.form.UpdateBasketDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void SaveProductInBasketThatExist() {
        Long userID = 1L;
        Long expectedId = 2L;
        int expectedQuantity = 3;

         var item = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));
        System.out.println(item);

        assertEquals(expectedId, item.getProduct().getId());
        assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    void SaveProductInBasketThatDoesNotExist() {
        Long userID = 3L;
        Long expectedId = 2L;
        int expectedQuantity = 3;

        var item = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));
        System.out.println(item);

        assertEquals(expectedId, item.getProduct().getId());
        assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    void tryToSaveProductThatDoesNotExistWillFail() {
        Long userID = 3L; // todo
        Long expectedId = 2L;
        int expectedQuantity = 3;

        var item = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));
        System.out.println(item);

        assertEquals(expectedId, item.getProduct().getId());
        assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    void tryToSaveNegativeAmountOfProductWillFail() {
        fail();
    }

    @Test
    @Disabled
    void saveWithUserThatDoesNotExist() {
        // this should not be possible the controller will check for authority
        fail();

    }

}