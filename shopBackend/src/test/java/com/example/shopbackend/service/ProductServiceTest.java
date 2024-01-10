package com.example.shopbackend.service;

import com.example.shopbackend.entity.Product;
import com.example.shopbackend.model.ProductDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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
    @Disabled
    void save() {
        var product = new Product("Created by test", "A short description written by test", 123);

        var expected = new ProductDTO(product);

        var actual = productService.save(expected);

//        assertEquals(expected.id(), null); // Cant check for id its created when a product is saved
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.price(), actual.price());
    }

    @Test
    void FailUpdateIdDoesNotExist() {
        var expected = productService.findById(1L);// Product("Created by test", "A short description written by test", 123);
            expected.setId(98235L); //
        var badId = new ProductDTO(expected);
        var actual = productService.update(badId);

        assertNull(actual);


    }
    @Test
    void SuccessfullyUpdate() {
        String expectedName = "updated Name";
        String expectedDesc = "updated desc";
        int expectedPrice = 42;

        var product = productService.findById(1L);// Product("Created by test", "A short description written by test", 123);
        product.setName(expectedName);
        product.setDescription(expectedDesc);
        product.setPrice(expectedPrice);
        var expected = new ProductDTO(product);
        var actual = productService.update(expected);

        assertEquals(expected.id(), actual.getId());
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.price(), actual.getPrice());

    }

    @Test
    @Disabled
    void SuccessfullyDelete() {
        // created preconditions
        var product = new Product("Created to be deleted", "A short description for a product to delete", 123);
        var productDTO = new ProductDTO(product);



        var savedProduct = productService.save(productDTO);

        // perform action
        boolean actual = productService.delete(savedProduct.id());

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