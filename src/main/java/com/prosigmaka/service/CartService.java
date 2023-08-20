package com.prosigmaka.service;

import com.prosigmaka.entity.Cart;

import java.util.List;

public interface CartService {

    boolean isExist(String username);

    Cart create(String username);

    Cart getCart(String username);

    Cart addItem(String username, Long productId);

    Cart update(String username, Cart body);

    List<Cart> getAllOrders(String username);
}
