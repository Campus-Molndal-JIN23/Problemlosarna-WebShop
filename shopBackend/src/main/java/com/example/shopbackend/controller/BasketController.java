package com.example.shopbackend.controller;

import com.example.shopbackend.entity.BasketOld;
import com.example.shopbackend.entity.ProductDTO;
import com.example.shopbackend.entity.ProductOld;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/webshop/basket")
public class BasketController {

    private static final Logger LOG = LoggerFactory.getLogger(BasketController.class);
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketDTO> getBasket(@PathVariable Long id) {

        BasketDTO basket = basketService.getBasket(2L);
        LOG.info("controller info " + basket.toString());
        if (basket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(basket);

    }


    @PostMapping("") // this is a product id
    public ResponseEntity<?> addProductToBasket(@RequestBody ProductDTO payload) {

        // TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<ProductOld, Integer> products = new HashMap<>();

        products.put(new ProductOld("Product 1", 100, "Text about the product 1"), 2);
        products.put(new ProductOld("added Product 4", 400, " not realy added but you get the idea"), 1);
        products.put(new ProductOld("Product 3", 300, "Text about the product 3"), 1);
        products.put(new ProductOld("Product 2", 200, "Text about the product 2"), 5);
        products.put(payload.product(), payload.quantity());

        var basket = new BasketOld(80085, products);

        return ResponseEntity.ok(basket);
    }

    @PutMapping("")
    public ResponseEntity<?> updateQuantity(@RequestBody ProductDTO payload) {
// TODO update code and Contract, we need a @RequestBody for this with Product and count

        HashMap<ProductOld, Integer> products = new HashMap<>();

        products.put(new ProductOld("Product 1", 100, "Text about the product 1"), 2);
        products.put(new ProductOld("Product 2", 200, "Text about the product 2"), 5);
        products.put(new ProductOld("Product 3", 300, "Text about the product 3"), 1);
        products.put(new ProductOld("added Product 4", 400, " not really added but you get the idea"), 1);
        products.put(payload.product(), payload.quantity());

        var basket = new BasketOld(80085, products);

        return ResponseEntity.ok(basket);
    }


    @DeleteMapping("")
    public ResponseEntity<?> deleteProduct(@RequestBody ProductOld product) {
        return ResponseEntity.noContent().build();
    }


}
