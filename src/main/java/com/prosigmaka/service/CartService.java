package com.prosigmaka.service;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.entity.CartItem;

import java.util.List;

public interface CartService {

    boolean isExist(String username);

    Cart create(String username);

    Cart getCart(String username);

    CartItem addItem(String username, Long productId);

    Cart update(String username, Cart body);

    List<Cart> getAllOrders(String username);
}
