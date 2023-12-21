package com.example.shopfrontend.controller;

import com.example.shopfrontend.form.LoginForm;
import com.example.shopfrontend.form.LoginResponse;
import com.example.shopfrontend.form.RegistrationForm;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.dto.ProductDTO;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;
import java.util.List;

@Controller

public class IndexController {

    private final ProductHttp productHttp;
    private final UserHttp userHttp;
    public static LoginResponse currentUser = new LoginResponse();

    private final String ERROR_URL = "/error";
    private final String ADMIN_ROLE = "ROLE_ADMIN";
    private final String USER_ROLE = "ROLE_USER";
    String errorMessage = "";
    int status = 0;

    public IndexController(ProductHttp productHttp, UserHttp userHttp) {
        this.productHttp = productHttp;
        this.userHttp = userHttp;
    }

    @GetMapping("/index")
    public String listProducts(Model model) throws IOException, ParseException {
        List<ProductDTO> products = productHttp.getAllProducts();
        if(products == null) {
            return "redirect:" + ERROR_URL;
        } else {
            model.addAttribute("products", products);
            return "index";
        }
    }

    @GetMapping("/index/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        ProductDTO product = productHttp.getProductById(id);
        if(product == null) {
            return "redirect:" + ERROR_URL;
        } else {
            model1.addAttribute("product", product);
            return "view_one_product";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        LoginForm loginform = new LoginForm();
        model.addAttribute("loginform", loginform);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(LoginForm user) throws IOException, ParseException {
        status = userHttp.loginUser(user);

        if (status == 401) {
            errorMessage = "Wrong username or password";
            return "redirect:/registration";
        }
        if (status == 404) {
            errorMessage = "User not found";
            return "redirect:/registration";
        }
        else {
            if (currentUser.getRole().contains(ADMIN_ROLE)) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
        model.addAttribute("errorMessage", errorMessage);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(RegistrationForm form) throws IOException {
        status = userHttp.registerUser(form);
        if (status == 200) {
            return "redirect:/login";
        }
        if (status == 409) {
            errorMessage = "Username already exists";
        } else {
            errorMessage = "Something went wrong with the registration";
        }
        return "redirect:/registration";
    }

    @GetMapping("/logout")
    public static String logout() {
        currentUser = new LoginResponse();
        return "redirect:/index";
    }

    @GetMapping("/error")
    public String error(Model model) {

        if (currentUser.getRole().contains(ADMIN_ROLE)) {
            model.addAttribute("home", "/admin");
        }
        if (currentUser.getRole().contains(USER_ROLE)) {
            model.addAttribute("home", "/user");
        } else {
            model.addAttribute("home", "/index");
        }
        return "error";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model) {
        if (currentUser.getRole().contains("ROLE_ADMIN")) {
            model.addAttribute("home", "/admin");
        }
        if (currentUser.getRole().contains("ROLE_USER")) {
            model.addAttribute("home", "/user");
        } else {
            model.addAttribute("home", "/index");
        }
        return "unauthorized";
    }
}
