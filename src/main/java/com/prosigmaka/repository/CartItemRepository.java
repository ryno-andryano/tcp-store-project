package com.prosigmaka.repository;

import com.prosigmaka.entity.CartItem;
import com.prosigmaka.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    CartItem findByCartItemId(CartItemId cartItemId);
}
