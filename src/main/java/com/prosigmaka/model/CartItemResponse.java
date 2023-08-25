package com.prosigmaka.model;

import com.prosigmaka.entity.CartItem;
import lombok.Getter;

@Getter
public class CartItemResponse {

    private final long productId;
    private final String productName;
    private final String productImage;
    private final long price;
    private final int quantity;
    private final long subtotal;

    public CartItemResponse(CartItem i) {
        this.productId = i.getCartItemId().getProduct().getId();
        this.productName = i.getCartItemId().getProduct().getName();
        if (!i.getCartItemId().getProduct().getImage().isEmpty())
            this.productImage = i.getCartItemId().getProduct().getImage().get(0);
        else this.productImage = null;
        this.price = i.getCartItemId().getProduct().getPrice();
        this.quantity = i.getQuantity();
        this.subtotal = i.getSubtotal();
    }
}
