package com.example.shopfrontend.controller;


import com.example.shopfrontend.http.BasketHttp;
import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.BasketDTO;
import com.example.shopfrontend.models.UpdateBasketDTO;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

import com.example.shopfrontend.http.UserHttp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {


    private  ProductHttp productHttp;
    private OrderHttp orderHttp;

    private UserHttp userHttp;
    private BasketHttp basketHttp;



    public UserController(ProductHttp productHttp, OrderHttp orderHttp, UserHttp userHttp, BasketHttp basketHttp) {
        this.productHttp = productHttp;
        this.orderHttp = orderHttp;
        this.userHttp = userHttp;
        this.basketHttp = basketHttp;
    }

    @GetMapping("/user")
    public String userIndex(Model model) throws IOException, ParseException {

        model.addAttribute("products", productHttp.getAllProducts());
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("newProduct", new UpdateBasketDTO());
        return "user_index";
    }


    @GetMapping("/user/basket")
    public String getBasket(Model model) throws IOException, ParseException {
        model.addAttribute("username", IndexController.currentUser.getUsername());
        BasketDTO basket = basketHttp.getBasket(IndexController.currentUser.getToken());
        model.addAttribute("newProduct", new UpdateBasketDTO());
        model.addAttribute("basket", basket);
        return "user_basket";
    }

    @PostMapping("/user/basket/add/{id}")
    public String addToBasket(@PathVariable long id , @ModelAttribute UpdateBasketDTO newProduct) throws IOException, ParseException {
        log.info("addToBasket: " + id + " " + newProduct);
        newProduct.setProductId(id);
        basketHttp.addProductToBasket(newProduct, IndexController.currentUser.getToken());
        return "redirect:/user";
    }

    @PostMapping("/user/basket/edit/{id}")
    public String updateBasketItem(@PathVariable long id ,@ModelAttribute UpdateBasketDTO newProduct) throws IOException {
        log.info("updateBasketItem: " + id + " " + newProduct);
        newProduct.setProductId(id);
        basketHttp.updateProductQuantityInBasket(newProduct, IndexController.currentUser.getToken());
        return "redirect:/user/basket";
    }

    @GetMapping("/user/basket/remove/{id}")
    public String removeBasketItem(@PathVariable long id) throws IOException {
        UpdateBasketDTO itemToRemove = new UpdateBasketDTO();
        itemToRemove.setProductId(id);
        log.info("removeBasketItem: " + itemToRemove);
        basketHttp.removeProductFromBasket(itemToRemove, IndexController.currentUser.getToken());
        return "redirect:/user/basket";

    }

    @GetMapping("/user/orders")
    public String getOrders(Model model) throws IOException, ParseException {
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("orders", orderHttp.getAllOrdersForOne(IndexController.currentUser.getToken()));
        return "user_past_orders";
    }

    @GetMapping("/user/checkout")
    public String checkoutBasket () throws IOException, ParseException {
        orderHttp.placeOrder(IndexController.currentUser.getToken());
        return "redirect:/user";
    }
}
