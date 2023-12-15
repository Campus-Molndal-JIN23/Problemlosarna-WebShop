package com.example.shopbackend.controller;

import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.service.BasketService;
import com.example.shopbackend.security.ExtractData;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/webshop/basket")
public class BasketController {

    private final BasketService basketService;
    private final ExtractData extractData;

    public BasketController(BasketService basketService, ExtractData extractData) {
        this.basketService = basketService;
        this.extractData = extractData;
    }

    @GetMapping("")
    public ResponseEntity<BasketDTO> getBasket(Authentication authentication, Principal principal) {
        System.out.println("Principal: " + principal.toString());
//        System.out.println("Authentication: " + authentication.toString()); // is null
        String userid = principal.getName();

//        System.out.println(userid);
//        BasketDTO basket = basketService.getBasket(userid);
        BasketDTO basket = null;
        return basket != null ? ResponseEntity.ok(basket) : ResponseEntity.notFound().build();
    }


    @PostMapping("") // this is a product id
    public ResponseEntity<?> addProductToBasket(@RequestBody UpdateBasketDTO payload, String jwt) {

        long userid = extractData.getUserID(jwt);
        var updatedBasket = basketService.addProduct(userid, payload);

        return updatedBasket != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("")
    public ResponseEntity<?> updateQuantity(@RequestBody UpdateBasketDTO payload, String jwt) {

        long userid = extractData.getUserID(jwt);
        var updatedBasket = basketService.updateQuantityProduct(userid, payload);

        return updatedBasket != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteProduct(@RequestBody UpdateBasketDTO payload, String jwt) {

        long userid = extractData.getUserID(jwt);
        return basketService.deleteProductActiveBasket(userid, payload) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
