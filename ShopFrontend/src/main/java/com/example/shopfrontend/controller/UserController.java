package com.example.shopfrontend.controller;


import com.example.shopfrontend.http.BasketHttp;
import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.*;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

import com.example.shopfrontend.http.UserHttp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {


    private final ProductHttp productHttp;
    private final OrderHttp orderHttp;
    private final BasketHttp basketHttp;
    private final UserHttp userHttp;

    String message = "";

    int status = 0;



    public UserController(ProductHttp productHttp, OrderHttp orderHttp, BasketHttp basketHttp, UserHttp userHttp) {
        this.productHttp = productHttp;
        this.orderHttp = orderHttp;
        this.basketHttp = basketHttp;
        this.userHttp = userHttp;
    }

    @GetMapping("/user")
    public String userIndex(Model model) throws IOException, ParseException {
        List<ProductDTO> products = productHttp.getAllProducts();
        if(products == null) {
            return "redirect:/error";
        } else {
            model.addAttribute("products", products);
            model.addAttribute("username", IndexController.currentUser.getUsername());
            model.addAttribute("newProduct", new UpdateBasketDTO());
            model.addAttribute("message", message);
            return "user_index";
        }
    }


    @GetMapping("/user/basket")
    public String getBasket(Model model) throws IOException, ParseException {
        BasketDTO basket = basketHttp.getBasket(IndexController.currentUser.getToken());
        if(basket == null) {
            return "redirect:/error";
        }
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("newProduct", new UpdateBasketDTO());
        model.addAttribute("basket", basket);
        return "user_basket";
    }

    @GetMapping("/user/basket/add/{id}")
    public String addToBasket(@PathVariable long id) throws IOException, ParseException {
        UpdateBasketDTO newProduct = new UpdateBasketDTO();
        newProduct.setProductId(id);
        newProduct.setQuantity(1);
        status = basketHttp.addProductToBasket(newProduct, IndexController.currentUser.getToken());
        if (status == 200) {
            return "redirect:/user";
        }
        if (status == 401 || status == 403) {
            return "redirect:/unauthorized";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/user/basket/edit/{id}")
    public String updateBasketItem(@PathVariable long id ,@ModelAttribute UpdateBasketDTO newProduct) throws IOException {
        log.info("updateBasketItem: " + id + " " + newProduct);
        newProduct.setProductId(id);
        status = basketHttp.updateProductQuantityInBasket(newProduct, IndexController.currentUser.getToken());
        if(status == 200) {
            return "redirect:/user/basket";
        }
        if (status == 401 || status == 403) {
            return "redirect:/unauthorized";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/user/basket/remove/{id}")
    public String removeBasketItem(@PathVariable long id) throws IOException {
        UpdateBasketDTO itemToRemove = new UpdateBasketDTO();
        itemToRemove.setProductId(id);
        log.info("removeBasketItem: " + itemToRemove);
        status = basketHttp.removeProductFromBasket(itemToRemove, IndexController.currentUser.getToken());
        if(status == 200) {
            return "redirect:/user/basket";
        }
        if (status == 401 || status == 403) {
            return "redirect:/unauthorized";
        } else {
            return "redirect:/error";
        }
    }


    @GetMapping("/user/details")
    public String viewUserDetails(Model model) throws IOException, ParseException {
        //TODO vad får vi för objekt tillbaka?
        Object user = userHttp.getUserDetails(IndexController.currentUser.getToken());
        if (user == null) {
            return "redirect:/error";
        }
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping("/user/orders")
    public String getOrders(Model model) throws IOException, ParseException {
        OrderDTO orders = orderHttp.getAllOrdersForOne(IndexController.currentUser.getToken());
        if(orders == null) {
            return "redirect:/error";
        }
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("pastOrders", orders);
        return "user_past_orders";
    }

    @GetMapping("/user/checkout")
    public String checkoutBasket () throws IOException, ParseException {
        int status = orderHttp.placeOrder(IndexController.currentUser.getToken());
        if (status == 204) {
            return "redirect:/user";
        }
        else {
            return "redirect:/unauthorized";
        }

    }
}
