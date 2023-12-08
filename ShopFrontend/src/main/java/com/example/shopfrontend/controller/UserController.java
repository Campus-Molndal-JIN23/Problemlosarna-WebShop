package com.example.shopfrontend.controller;


import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class UserController {


    private  ProductHttp productHttp;
    private OrderHttp orderHttp;

    private UserHttp userHttp;

    public UserController(ProductHttp productHttp, OrderHttp orderHttp, UserHttp userHttp) {
        this.productHttp = productHttp;
        this.orderHttp = orderHttp;
        this.userHttp = userHttp;
    }

    @GetMapping("/user")
    public String userIndex(Model model) throws IOException, ParseException {
        model.addAttribute("products", productHttp.getAllProducts());
        return "user_index";
    }

    @GetMapping("/user/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        model1.addAttribute("product", productHttp.getProductById(id));
        return "user_view_one_product";
    }


    @GetMapping("/user/basket")
    public String getBasket(Model model) throws IOException, ParseException {
        // Retrieve basket items based on the current user's token
        List<Product> basketItems = (List<Product>) userHttp.getBasket(IndexController.currentUser.getToken());
        if (basketItems != null) {
            model.addAttribute("basketItems", basketItems);
        } else {
            // Handle the case where basketItems is null, e.g., show an error message
            model.addAttribute("errorMessage", "Error fetching basket items");
        }
        return "user_basket";
    }

    //TODO needs produkt id, what do we send?
    @PostMapping("/user/basket/add")
    public String addToBasket(Product basketItem) throws IOException {
        // Add a product to the user's basket
        userHttp.addProductToBasket(IndexController.currentUser.getToken(), basketItem);
        return "redirect:/user/basket";
    }

    /*@GetMapping("/user/basket/edit/{id}")
    public String updateBasketItemForm(@PathVariable long id, Model model) throws IOException, ParseException {
        // Display form to update quantity for a specific basket item
        model.addAttribute("basketItem", userHttp.getBasketItemById(id, IndexController.currentUser.getToken()));
        return "update_basket_item";
    }*/

    /*
    @PostMapping("/user/basket/edit/{id}")
    public String updateBasketItem(@PathVariable long id, @ModelAttribute Product basketItem) throws IOException {
        // Update the quantity of a product in the user's basket
        Product itemToUpdate = userHttp.getBasketItemById(id, IndexController.currentUser.getToken());
        itemToUpdate.setQuantity(basketItem.getQuantity());
        userHttp.updateProductQuantityInBasket(IndexController.currentUser.getToken(), itemToUpdate);
        return "redirect:/user/basket";
    }
     */

    @GetMapping("/user/basket/remove/{id}")
    public String removeBasketItem(@PathVariable long id) throws IOException {
        // Remove a product from the user's basket
        userHttp.removeProductFromBasket(IndexController.currentUser.getToken(), id);
        return "redirect:/user/basket";

    }

    @GetMapping("/user/orders")
    public String getOrders(Model model) throws IOException, ParseException {
        model.addAttribute("orders", orderHttp.getAllOrders(IndexController.currentUser.getToken()));
        return "user_past_orders";
    }
}
