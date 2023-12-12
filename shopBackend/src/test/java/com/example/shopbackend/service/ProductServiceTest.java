package com.example.shopbackend.service;

import com.example.shopbackend.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// todo add test profile

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void findAll() {
        assertNotNull(productService.findAll());
    }

    @Test
    void findById() {
        Long expected = 1L;
        var prod = productService.findById(expected);
        assertEquals(expected, prod.getId());
    }

    @Test
    void findNothing() {
        Long expected = 99999999L;
        var actual = productService.findById(expected);
        assertNull(actual);
    }

    @Test
    void save() {
        var expected = new Product("Created by test", "A short description written by test", 123);
        var actual = productService.save(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    void FailUpdateIdDoesNotExist() {
        var expected = productService.findById(1L);// Product("Created by test", "A short description written by test", 123);
            expected.setId(98235L);
        var actual = productService.update(expected);

        assertNull(actual);


    }
    @Test
    void SuccessfullyUpdate() {
        String expectedName = "updated Name";
        String expectedDesc = "updated desc";
        int expectedPrice = 42;

        var expected = productService.findById(1L);// Product("Created by test", "A short description written by test", 123);
        expected.setName(expectedName);
        expected.setDescription(expectedDesc);
        expected.setPrice(expectedPrice);

        var actual = productService.update(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());

    }

    @Test
    void SuccessfullyDelete() {
        // created preconditions
        var product = new Product("Created to be deleted", "A short description for a product to delete", 123);
        var savedProduct = productService.save(product);

        // perform action
        boolean actual = productService.delete(savedProduct.getId());

        assertTrue(actual);
    }
    @Test
    void FailDeleteDoesNotExist() {
        // created preconditions
        var product = new Product("Created to be deleted", "A short description for a product to delete", 123);
        product.setId(2139435345L);

        // perform action
        boolean actual = productService.delete(product.getId());

        assertFalse(actual);
    }
}