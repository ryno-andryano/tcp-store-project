package com.prosigmaka.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class WebController {

    @GetMapping({"/", "/home"})
    public ModelAndView displayHomePage() {
        return new ModelAndView("home");
    }

    @GetMapping("/error404")
    public ModelAndView displayNotFoundPage() {
        return new ModelAndView("not-found");
    }

    @GetMapping("/products")
    public ModelAndView displayProductsPage() {
        return new ModelAndView("products");
    }

    @GetMapping("/product/{id}")
    public ModelAndView displayProductDetailPage() {
        return new ModelAndView("product-detail");
    }

    @GetMapping({"/product/add", "/product/{id}/edit"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView displayProductFormPage(@PathVariable(required = false) String id) {
        if (id == null) return new ModelAndView("product-form", "title", "Add Product");
        else return new ModelAndView("product-form", "title", "Edit Product");
    }

    @GetMapping("/login")
    public ModelAndView displayLoginFormPage(Principal principal) {
        if (principal != null) return new ModelAndView("redirect:/home");
        return new ModelAndView("login-form");
    }

    @GetMapping("/register")
    public ModelAndView displayRegisterPage(Principal principal) {
        if (principal != null) return new ModelAndView("redirect:/home");
        return new ModelAndView("user-form", "title", "Register");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView displayUsersPage() {
        return new ModelAndView("users");
    }

    @GetMapping({"/user/add", "/user/{username}/edit"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public ModelAndView displayUserFormPage(@PathVariable(required = false) String username) {
        if (username == null) return new ModelAndView("user-form", "title", "Add User");
        else return new ModelAndView("user-form", "title", "Edit User");
    }

    @GetMapping("/cart")
    public ModelAndView displayCartPage(Principal principal) {
        if (principal == null) return new ModelAndView("redirect:/login");
        else return new ModelAndView("cart");
    }

    @GetMapping("/order-history")
    public ModelAndView displayOrderHistoryPage() {
        return new ModelAndView("order");
    }

}
