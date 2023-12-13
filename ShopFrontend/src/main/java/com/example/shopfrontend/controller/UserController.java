package com.example.shopfrontend.controller;


import com.example.shopfrontend.http.BasketHttp;
import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.Basket;
import com.example.shopfrontend.models.OrderQty;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
public class UserController {


    private  ProductHttp productHttp;
    private OrderHttp orderHttp;

    private UserHttp userHttp;
    private BasketHttp basketHttp;

    private int quantity = 1;

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
        model.addAttribute("quantity", quantity);
        return "user_index";
    }

    @GetMapping("/user/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        model1.addAttribute("product", productHttp.getProductById(id));
        return "user_view_one_product";
    }


    @GetMapping("/user/basket")
    public String getBasket(Model model) throws IOException, ParseException {
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("quantity", quantity);
        Basket basket = basketHttp.getBasket(IndexController.currentUser.getToken());
        model.addAttribute("basket", basket);
        return "user_basket";
    }

    @PostMapping("/user/basket/add/{id}+{quantity}")
    public String addToBasket(@PathVariable int productId ,@PathVariable int quantity) throws IOException, ParseException {
        OrderQty product = new OrderQty();
        product.setId(productId);
        product.setQuantity(quantity);
        basketHttp.addProductToBasket(product, IndexController.currentUser.getToken());
        return "redirect:/user/basket";
    }

    // TODO maybe not needed
    @GetMapping("/user/basket/edit/{id}")
    public String updateBasketItemForm(@PathVariable long id, Model model) throws IOException, ParseException {
        //model.addAttribute("basketItem", basketHttp.getBasketItemById(id, IndexController.currentUser.getToken()));
        return "update_basket_item";
    }


    @PostMapping("/user/basket/edit/{id}+{quantity}")
    public String updateBasketItem(@PathVariable int id ,@PathVariable int quantity) throws IOException {
        OrderQty itemToUpdate = new OrderQty();
        itemToUpdate.setId(id);
        itemToUpdate.setQuantity(quantity);
        basketHttp.updateProductQuantityInBasket(itemToUpdate, IndexController.currentUser.getToken());
        return "redirect:/user/basket";
    }


    @GetMapping("/user/basket/remove/{id}")
    public String removeBasketItem(@PathVariable long id) throws IOException {
        OrderQty itemToRemove = new OrderQty();
        itemToRemove.setId(id);
        basketHttp.removeProductFromBasket(itemToRemove, IndexController.currentUser.getToken());
        return "redirect:/user/basket";

    }

    @GetMapping("/user/orders")
    public String getOrders(Model model) throws IOException, ParseException {
        model.addAttribute("username", IndexController.currentUser.getUsername());
        model.addAttribute("orders", orderHttp.getAllOrdersForOne(IndexController.currentUser.getToken()));
        return "user_past_orders";
    }
}
