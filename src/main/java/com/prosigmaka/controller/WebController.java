package com.prosigmaka.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @GetMapping("/")
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
}
