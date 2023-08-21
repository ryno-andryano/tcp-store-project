package com.prosigmaka.service;

import com.prosigmaka.entity.*;
import com.prosigmaka.repository.CartItemRepository;
import com.prosigmaka.repository.CartRepository;
import com.prosigmaka.repository.ProductRepository;
import com.prosigmaka.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    final private CartRepository cartRepository;
    final private CartItemRepository cartItemRepository;
    final private UserRepository userRepository;
    final private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String username) {
        return cartRepository.existsByUserAndPaidStatus(
                userRepository.findByUsername(username).orElseThrow(),
                "PENDING"
        );
    }

    @Override
    @Transactional
    public Cart create(String username) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findByUsername(username).orElseThrow());
        cart.setPaidStatus("PENDING");
        cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCart(String username) {
        return cartRepository.findByUserAndPaidStatus(
                userRepository.findByUsername(username).orElseThrow(),
                "PENDING"
        );
    }

    @Override
    @Transactional
    public CartItem addItem(String username, Long productId) {
        Cart cart;
        if (!isExist(username)) {
            cart = create(username);
        } else {
            cart = getCart(username);
        }
        Product product = productRepository.findById(productId).orElseThrow();

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
        cartItemRepository.saveAndFlush(cartItem);

        cart.setTotal(cart.getTotal() + product.getPrice());
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    @Transactional
    public Cart update(String username, Cart reqCart) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Cart cart = cartRepository.findByUserAndPaidStatus(user, "PENDING");

        if (!reqCart.getPaymentMethod().equals("NOT_SET")) cart.setPaymentMethod(reqCart.getPaymentMethod());
        if (!reqCart.getDeliveryMethod().equals("NOT_SET")) cart.setDeliveryMethod(reqCart.getDeliveryMethod());
        if (reqCart.getPaidStatus().equals("COMPLETED")) {
            cart.setPaidStatus("COMPLETED");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cart.setOrderDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(timestamp));
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> getAllOrders(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartRepository.findAllByUserAndPaidStatus(user, "COMPLETED");
    }

}
