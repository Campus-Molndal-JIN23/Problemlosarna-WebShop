package com.example.shopbackend.service;

import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Slf4j
@SpringBootTest
class BasketServiceTest {

    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductService productService;


    @Test
    void getAExistingBasket() {
        var basket = basketService.getBasket(2L);

        assertNotNull(basket);
    }

    @Test
    void tryGetABasketThatDontExist() {
        var basket = basketService.getBasket(998957943L);
        assertNull(basket);
    }

    @Test
    void SaveProductInBasketThatExist() {
        Long userID = 1L; // this won't work because the db is in the wrong state.
        Long expectedId = 6L;
        int expectedQuantity = 6;

        var actual = basketService.addProduct(userID, new UpdateBasketDTO(expectedId, expectedQuantity));
        log.info(actual.toString());
        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    void SaveProductInBasketThatDoesNotExistWillCreateANewBasket() {
        Long userID = 1L; // user should not have any baskets or orders
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
    void checkThatIdAndTotalCostIsCorrect() {
        Long userID = 6L; // fresh user with no baskets
        Long expectedProductId = 2L; // price 100
        int expectedQuantity = 1;

        long expectedPrice = 0;
        var initialBasket = basketService.getBasket(userID);
        expectedPrice = initialBasket == null ? 0 : initialBasket.getTotalCost();
        expectedPrice += productService.findById(expectedProductId).getPrice() * expectedQuantity;

        basketService.addProduct(userID, new UpdateBasketDTO(expectedProductId, expectedQuantity));

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

        assertNull(actual);
    }
    @Test
    void updateToMoreOfAProductThatAlreadyInTheBasket() {

        // get a basket with known conditions
        Long userID = 2L;
        BasketDTO basket = basketService.getBasket(userID);
        Long expectedId = basket.getProducts().getFirst().getId();
        int expectedQty = basket.getProducts().getFirst().getQuantity() + 32;
        //update item
        var updateDTO = new UpdateBasketDTO(expectedId, expectedQty);
        var actual = basketService.updateQuantityProduct(userID, updateDTO);

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQty, actual.getQuantity());
//        assertNull(actual);
    }

    @Test
    void addLessOfAProductThatAlreadyInTheBasket() {
        // get a basket with known conditions
        Long userID = 2L;
        BasketDTO basket = basketService.getBasket(userID);
        Long expectedId = basket.getProducts().getLast().getId();
        int expectedQty = basket.getProducts().getLast().getQuantity() - 1;
        //try to add product
        var updateDTO = new UpdateBasketDTO(expectedId, expectedQty);
        var actual = basketService.addProduct(userID, updateDTO);
//        System.out.println(actual);

        assertNull(actual);
    }
    @Test
    void updateToLessOfAProductThatAlreadyInTheBasket() {
        // get a basket with known conditions
        Long userID = 2L;
        BasketDTO basket = basketService.getBasket(userID);
        Long expectedId = basket.getProducts().getLast().getId();
        int expectedQty = basket.getProducts().getLast().getQuantity() - 1;
        //update item
        var updateDTO = new UpdateBasketDTO(expectedId, expectedQty);
        var actual = basketService.updateQuantityProduct(userID, updateDTO);

        assertEquals(expectedId, actual.getProduct().getId());
        assertEquals(expectedQty, actual.getQuantity());
    }

    @Test
    void deleteItemThatExistInBasket() {
        Long userID = 2L;
        Long expectedId = 2L;
        int expectedQuantity = 0;
        var precondition = basketService.getBasket(userID);

        basketService.deleteProductActiveBasket(userID, new UpdateBasketDTO(expectedId, expectedQuantity));

        var actual = basketService.getBasket(userID);
        System.out.println("pre: " + precondition);
        System.out.println("act: " + actual);

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