package com.example.shopbackend.controller;

import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/basket")
public class BasketController {

    private static final Logger LOG = LoggerFactory.getLogger(BasketController.class); // todo if no in use
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("")
    public ResponseEntity<BasketDTO> getBasket() {

        Long userID = 1L; // todo get id form token
        BasketDTO basket = basketService.getBasket(userID);
        return basket != null ? ResponseEntity.ok(basket) : ResponseEntity.notFound().build();
    }


    @PostMapping("") // this is a product id
    public ResponseEntity<?> addProductToBasket(@RequestBody UpdateBasketDTO payload) {

        Long userID = 1L; // todo get id form token
        var updatedBasket = basketService.addProduct(userID, payload);

        return updatedBasket != null ? ResponseEntity.ok(updatedBasket) : ResponseEntity.notFound().build();

    }

    @PutMapping("")
    public ResponseEntity<?> updateQuantity(@RequestBody UpdateBasketDTO payload) {
        // Yeah! It's the same as Post. If Checking for existence is crucial make that an early return.
        Long userID = 1L; // todo get id form token
        var updatedBasket = basketService.addProduct(userID, payload);

        return updatedBasket != null ? ResponseEntity.ok(updatedBasket) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("")
    public ResponseEntity<?> deleteProduct(@RequestBody UpdateBasketDTO payload) {

        Long userID = 1L; // todo get id form token
        var result = basketService.deleteProductActiveBasket(userID, payload);

        return ResponseEntity.noContent().build();
    }


}
