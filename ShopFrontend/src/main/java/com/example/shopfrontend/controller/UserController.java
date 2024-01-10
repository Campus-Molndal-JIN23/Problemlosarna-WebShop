package com.example.shopfrontend.controller;


import com.example.shopfrontend.http.BasketHttp;
import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.dto.BasketDTO;
import com.example.shopfrontend.models.dto.OrderDTO;
import com.example.shopfrontend.models.dto.ProductDTO;
import com.example.shopfrontend.models.dto.UpdateBasketDTO;
import com.example.shopfrontend.models.userDetails.UserDTO;
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

import static com.example.shopfrontend.controller.IndexController.currentUser;

/** This class is responsible for handling requests from the user.
 * It checks if the user is logged in , not if the role is user.
 * this is because the admin must be able to test the user pages.
 * it uses a Logger to log the requests and responses to the console.
 */
@Slf4j
@Controller
public class UserController {


    private final ProductHttp productHttp;
    private final OrderHttp orderHttp;
    private final BasketHttp basketHttp;
    private final UserHttp userHttp;

    // the following variables are used to redirect the user to the appropriate page
    private final String UNAUTHORIZED_URL = "/unauthorized";
    private final String ERROR_URL = "/error";
    private final String USER_URL = "/user";
    private final String USERBASKET_URL = "/user/basket";

    private BasketDTO basket = new BasketDTO();
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
        if (currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }

        basket = basketHttp.getBasket(currentUser.getToken());
        List<ProductDTO> products = productHttp.getAllProducts();
        if (products == null) {
            log.info("products not found");
            return "redirect:" + ERROR_URL;
        } else {
            model.addAttribute("products", products);
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("newProduct", new UpdateBasketDTO());
            model.addAttribute("message", message);
            model.addAttribute("basket", basket);
            return "user_index";
        }
    }

    @GetMapping("/user/basket")
    public String getBasket(Model model) throws IOException, ParseException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }

        basket = basketHttp.getBasket(currentUser.getToken());
        if(basket == null) {
            log.info("basket empty");
            return "user_basket_empty";
        }
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("newProduct", new UpdateBasketDTO());
        model.addAttribute("basket", basket);
        return "user_basket";
    }

    @GetMapping("/user/basket/add/{id}")
    public String addToBasket(@PathVariable long id) throws IOException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }
        UpdateBasketDTO newProduct = new UpdateBasketDTO();
        newProduct.setProductId(id);
        newProduct.setQuantity(1);
        status = basketHttp.addProductToBasket(newProduct, currentUser.getToken());
        if (status == 200 || status == 400) {
            log.info("addToBasket: " + newProduct);
            return "redirect:" + USER_URL;
        }
        if (status == 401 || status == 403) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        } else {
            log.info("error");
            return "redirect:" + ERROR_URL;
        }
    }

    @PostMapping("/user/basket/edit/{id}")
    public String updateBasketItem(@PathVariable long id ,@ModelAttribute UpdateBasketDTO newProduct) throws IOException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }

        newProduct.setProductId(id);
        status = basketHttp.updateProductQuantityInBasket(newProduct, currentUser.getToken());
        if(status == 200) {
            log.info("updateBasketItem: " + newProduct);
            return "redirect:/user/basket";
        }
        if (status == 401 || status == 403) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        } else {
            log.info("error");
            return "redirect:" + ERROR_URL;
        }
    }

    @GetMapping("/user/basket/remove/{id}")
    public String removeBasketItem(@PathVariable long id) throws IOException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }
        UpdateBasketDTO itemToRemove = new UpdateBasketDTO();
        itemToRemove.setProductId(id);

        status = basketHttp.removeProductFromBasket(itemToRemove, currentUser.getToken());
        if(status == 204) {
            log.info("removeBasketItem: " + itemToRemove);
            return "redirect:" + USERBASKET_URL;
        }
        if (status == 401 || status == 403) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        } else {
            log.info("error");
            return "redirect:" + ERROR_URL;
        }
    }

    @GetMapping("/user/details")
    public String viewUserDetails(Model model) throws IOException, ParseException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }

        UserDTO user = userHttp.getUserDetails(currentUser.getToken());
        basket = basketHttp.getBasket(currentUser.getToken());
        if (user == null) {
            log.info("user not found");
            return "redirect:" + ERROR_URL;
        }
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("basket", basket);
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping("/user/orders")
    public String getOrders(Model model) throws IOException, ParseException {
        if (currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }

        OrderDTO orders = orderHttp.getAllOrdersForOne(currentUser.getToken());
        basket = basketHttp.getBasket(currentUser.getToken());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("pastOrders", orders);
        model.addAttribute("basket", basket);
        if (orders == null) {
            log.info("orders not found");
            return "user_past_orders_empty";
        }
        return "user_past_orders";
    }

    @GetMapping("/user/checkout")
    public String checkoutBasket () throws IOException {
        if(currentUser.getRole() == null) {
            log.info("not authorized");
            return "redirect:" + UNAUTHORIZED_URL;
        }
        status = orderHttp.placeOrder(currentUser.getToken());
        if (status == 200) {
            return "redirect:/user";
        }
        else {
            log.info("error");
            return "redirect:" + ERROR_URL;
        }
    }
}