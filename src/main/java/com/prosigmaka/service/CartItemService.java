package com.prosigmaka.service;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.entity.CartItem;
import com.prosigmaka.entity.CartItemId;
import com.prosigmaka.entity.Product;
import com.prosigmaka.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {
    final private CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public CartItem add(Cart cart, Product product) {
        CartItemId cartItemId = new CartItemId();
        cartItemId.setCart(cart);
        cartItemId.setProduct(product);

        CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCartItemId(cartItemId);
            cartItem.setQuantity(1);
            cartItem.setSubtotal(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setSubtotal(cartItem.getSubtotal() + product.getPrice());
        }
        return cartItemRepository.save(cartItem);
    }
}
