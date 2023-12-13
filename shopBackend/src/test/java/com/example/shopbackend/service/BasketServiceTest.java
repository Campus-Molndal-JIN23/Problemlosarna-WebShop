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

         var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void SaveProductInBasketThatDoesNotExistWillCreateANewBasket() {
        Long userID = 3L; // user should not have any baskets or orders
        Long expectedId = 2L;
        int expectedQuantity = 3;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void tryToSaveProductThatDoesNotExistWillFail() {
        Long userID = 1L; // todo
        Long productId = 9256754654L;
        int expectedQuantity = 3;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(productId ,expectedQuantity));

        assertNull(actual);
    }

    @Test
    void tryToSaveNegativeAmountOfProductWillFail() {
        Long userID = 1L; // todo
        Long expectedId = 2L;
        int expectedQuantity = -2;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId ,expectedQuantity));
        System.out.println(actual);

        assertNull(actual); // if negative quantity no object should be created

        /* This code was used to develop the solution
        assertEquals(expectedId, actual.getProduct().getId());
        assertTrue(actual.getQuantity() > 0);
         */

    }

    @Test
    @Disabled
    void saveWithUserThatDoesNotExist() {
        // this should not be possible the controller.class will check for authority
        fail();

    }

}