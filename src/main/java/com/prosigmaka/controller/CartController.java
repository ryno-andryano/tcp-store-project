package com.prosigmaka.controller;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.entity.CartItem;
import com.prosigmaka.model.CartItemResponse;
import com.prosigmaka.model.CartResponse;
import com.prosigmaka.model.OrderResponse;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.service.CartService;
import com.prosigmaka.service.ProductService;
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
    final private ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/api/product/{id}/cart")
    public ResponseEntity<ResponseEnvelope> addCartItem(
            @PathVariable(name = "id") Long productId,
            Principal principal
    ) {
        if (!productService.isExist(productId)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Product not found"), status);
        }

        String username = principal.getName();
        CartItem cartItem = cartService.addItem(username, productId);
        CartItemResponse result = new CartItemResponse(cartItem);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @GetMapping("/api/user/{username}/cart")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> getCart(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        } else if (!cartService.isCartExist(username)) {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(new ResponseEnvelope(status, null), status);
        }

        Cart cart = cartService.getCart(username);
        CartResponse result = new CartResponse(cart);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @PatchMapping("/api/user/{username}/cart")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> updateCart(@PathVariable String username, @RequestBody Cart reqCart) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        } else if (!cartService.isCartExist(username)) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Cart is empty"), status);
        }

        Cart cart = cartService.update(username, reqCart);
        CartResponse result = new CartResponse(cart);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @PatchMapping("/api/user/{username}/cart/checkout")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> checkoutCart(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        } else if (!cartService.isCartExist(username)) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Cart is empty"), status);
        }

        Cart order = cartService.checkout(username);
        OrderResponse result = new OrderResponse(order);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @GetMapping("/api/user/{username}/orders")
    @PreAuthorize("#username == principal.username")
    public ResponseEntity<ResponseEnvelope> getAllOrders(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        List<Cart> orderList = cartService.getAllOrders(username);
        List<OrderResponse> result = orderList.stream().map(OrderResponse::new).toList();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }
}
