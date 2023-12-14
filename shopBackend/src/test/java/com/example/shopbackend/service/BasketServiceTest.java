package com.example.shopbackend.service;

import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
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
        Long userID = 1L; // this won't work because the db is in wrong state.
        Long expectedId = 6L;
        int expectedQuantity = 6;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId, expectedQuantity));
        System.out.println(actual);
        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void SaveProductInBasketThatDoesNotExistWillCreateANewBasket() {
        Long userID = 3L; // user should not have any baskets or orders
        Long expectedId = 2L;
        int expectedQuantity = 3;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId, expectedQuantity));

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void tryToSaveProductThatDoesNotExistWillFail() {
        Long userID = 1L;
        Long productId = 9256754654L;
        int expectedQuantity = 3;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(productId, expectedQuantity));

        assertNull(actual);
    }

    @Test
    void tryToSaveNegativeAmountOfProductWillFail() {
        Long userID = 1L;
        Long expectedId = 2L;
        int expectedQuantity = -2; // negative

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId, expectedQuantity));
        System.out.println(actual);

        assertNull(actual); // if negative quantity no object should be created

        /* This code was used to develop the solution
        assertEquals(expectedId, actual.getProduct().getId());
        assertTrue(actual.getQuantity() > 0);
         */
    }

    @Test
    void tryToSaveZeroAmountOfProductWillFail() {
        Long userID = 1L;
        Long expectedId = 2L;
        int expectedQuantity = 0;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId, expectedQuantity));
        System.out.println(actual);

        assertNull(actual); // if Zero quantity, no object should be created
    }

    @Test
    @Disabled
    void saveWithUserThatDoesNotExist() {
        // this should not be possible the controller.class will check for authority
        Long userID = 8932L;
        Long expectedProductId = 2L;
        int expectedProductQuantity = 3;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedProductId, expectedProductQuantity));

        assertNull(actual);
    }

    @Test
    void checkThatIdAndTotalCostIsCorrect() {
        Long userID = 4L; // fresh user with no baskets
        Long expectedProductId = 1L; // price 100
        int expectedQuantity = 7;
        int expectedPrice = 100 * expectedQuantity;
        var added = basketService.addProduct(userID, new UpdateBasketDTO(expectedProductId, expectedQuantity));

        var actual = basketService.getBasket(userID);

        assertEquals(expectedProductId, actual.getProducts().getFirst().getId());
        assertEquals(expectedQuantity, actual.getProducts().getFirst().getQuantity());
        assertEquals(expectedPrice, actual.getTotalCost());
    }

    // test for using addProduct for update
    @Test
    void addMoreOfAProductThatAlreadyInTheBasket() {

        // get a basket with known conditions
        Long userID = 2L;
        BasketDTO basket = basketService.getBasket(userID);
        Long expectedId = basket.getProducts().getFirst().getId();
        int expectedQty = basket.getProducts().getFirst().getQuantity() + 32;
        //update item
        var updateDTO = new UpdateBasketDTO(expectedId, expectedQty);
        var actual = basketService.addProduct(userID, updateDTO);

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQty, actual.getQuantity());
    }

    @Test
    void addLessOfAProductThatAlreadyInTheBasket() {
        // get a basket with known conditions
        Long userID = 1L;
        BasketDTO basket = basketService.getBasket(userID);
        Long expectedId = basket.getProducts().getLast().getId();
        int expectedQty = basket.getProducts().getLast().getQuantity() - 1;
        //update item
        var updateDTO = new UpdateBasketDTO(expectedId, expectedQty);
        var actual = basketService.addProduct(userID, updateDTO);
        System.out.println(actual);
        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQty, actual.getQuantity());
    }

    @Test
    void deleteItemThatExistInBasket() {
        Long userID = 1L;
        Long expectedId = 2L;
        int expectedQuantity = 0;
        var precondition = basketService.getBasket(userID);

        basketService.deleteProductActiveBasket(userID, new UpdateBasketDTO(expectedId, expectedQuantity));

        var actual = basketService.getBasket(userID);
        System.out.println("pre" + precondition);
        System.out.println("act" + actual);

        // Assert that the product is not present in the basket after deletion
        assertTrue(actual.getProducts().stream().noneMatch(product -> product.getId().equals(expectedId)));
    }
    @Test

    void deleteItemThatDontExistInBasket() {
        Long userID = 4L;
        Long expectedId = 87324873024L;
        int expectedQuantity = 0;
        var precondition = basketService.getBasket(userID);

        basketService.deleteProductActiveBasket(userID, new UpdateBasketDTO(expectedId, expectedQuantity));

        var actual = basketService.getBasket(userID);
        System.out.println("pre" + precondition);
        System.out.println("act" + actual);

        // Assert that the product is not present in the basket after deletion
        assertTrue(actual.getProducts().stream().noneMatch(product -> product.getId().equals(expectedId)));
    }
}