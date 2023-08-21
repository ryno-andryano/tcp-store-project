package com.prosigmaka.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId;

    private int quantity;

    private long subtotal;

}


