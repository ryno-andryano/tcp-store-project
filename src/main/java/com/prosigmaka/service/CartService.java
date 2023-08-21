package com.prosigmaka.service;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.entity.CartItem;
import com.prosigmaka.entity.Product;
import com.prosigmaka.entity.User;
import com.prosigmaka.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CartService {

    final private CartRepository cartRepository;
    final private UserService userService;
    final private ProductService productService;
    final private CartItemService cartItemService;

    public CartService(CartRepository cartRepository, UserService userService, ProductService productService, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @Transactional(readOnly = true)
    public boolean isCartExist(String username) {
        return cartRepository.existsByUserAndPaidStatus(
                userService.get(username),
                "PENDING"
        );
    }

    @Transactional
    public Cart create(String username) {
        Cart cart = new Cart();
        cart.setUser(userService.get(username));
        cart.setPaidStatus("PENDING");
        cartRepository.save(cart);
        return cart;
    }

    @Transactional(readOnly = true)
    public Cart getCart(String username) {
        return cartRepository.findByUserAndPaidStatus(
                userService.get(username),
                "PENDING"
        );
    }

    @Transactional
    public CartItem addItem(String username, Long productId) {
        Cart cart;
        if (!isCartExist(username)) {
            cart = create(username);
        } else {
            cart = getCart(username);
        }
        Product product = productService.get(productId);
        cart.setTotal(cart.getTotal() + product.getPrice());

        cartRepository.save(cart);
        return cartItemService.add(cart, product);
    }

    @Transactional
    public Cart update(String username, Cart reqCart) {
        Cart cart = getCart(username);
        if (!reqCart.getPaymentMethod().equals("NOT_SET")) cart.setPaymentMethod(reqCart.getPaymentMethod());
        if (!reqCart.getDeliveryMethod().equals("NOT_SET")) cart.setDeliveryMethod(reqCart.getDeliveryMethod());
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart checkout(String username) {
        Cart cart = getCart(username);
        cart.setPaidStatus("COMPLETED");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cart.setOrderDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(timestamp));
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public List<Cart> getAllOrders(String username) {
        User user = userService.get(username);
        return cartRepository.findAllByUserAndPaidStatus(user, "COMPLETED");
    }

}
