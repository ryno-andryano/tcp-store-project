package com.prosigmaka.controller;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.service.CartService;
import com.prosigmaka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CartController {

    final private CartService cartService;
    final private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/api/product/{id}/cart")
    public ResponseEntity<ResponseEnvelope> addToCart(
            @PathVariable(name = "id") Long productId,
            Principal principal
    ) {
        String username = principal.getName();
        Cart cart = cartService.addItem(username, productId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, cart), status);
    }

    @GetMapping("/api/user/{username}/cart")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> getCart(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        Cart cart = cartService.getCart(username);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, cart), status);
    }

    @PatchMapping("/api/user/{username}/cart")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> updateCart(@PathVariable String username, @RequestBody Cart body) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        Cart cart = cartService.update(username, body);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, cart), status);
    }

    @GetMapping("/api/user/{username}/orders")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> getAllOrders(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }
        
        List<Cart> orderList = cartService.getAllOrders(username);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, orderList), status);
    }
}
