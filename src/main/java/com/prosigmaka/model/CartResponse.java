package com.prosigmaka.model;

import com.prosigmaka.entity.Cart;
import lombok.Getter;

import java.util.List;

@Getter
public class CartResponse {

    private final long orderId;
    private final String username;
    private final List<CartItemResponse> items;
    private final String paymentMethod;
    private final String deliveryMethod;
    private final long total;

    public CartResponse(Cart c) {
        this.orderId = c.getId();
        this.username = c.getUser().getUsername();
        this.items = c.getCartItems().stream().map(CartItemResponse::new).toList();
        this.total = c.getTotal();
        this.paymentMethod = c.getPaymentMethod();
        this.deliveryMethod = c.getDeliveryMethod();
    }
}
