package com.example.shopfrontend.controller;

import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.*;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;

@Controller
@AllArgsConstructor

public class IndexController {

    private ProductHttp productHttp;
    private  UserHttp userHttp;
    static LoginResponse currentUser = new LoginResponse();
    static String errorMessage = "";

    @GetMapping("/index")
    public String listProducts(Model model) throws IOException, ParseException {
        model.addAttribute("products", productHttp.getAllProducts());
        return "index";
    }

    @GetMapping("/index/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        model1.addAttribute("product", productHttp.getProductById(id));
        return "view_one_product";
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
        currentUser = userHttp.loginUser(user);

        if (currentUser == null) {
            errorMessage = "Wrong username or password or maybe you are not registered yet";
            return "redirect:/registration";
        }
        else {
            if (currentUser.getRole().equals("ADMIN")) {
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
    public String registerUser(RegistrationForm form, Model model) throws IOException, ParseException {
        int status = userHttp.registerUser(form);
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
    public String logout() {
        currentUser = new LoginResponse();
        return "redirect:/index";
    }
}
