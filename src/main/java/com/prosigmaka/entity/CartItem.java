package com.prosigmaka.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId;

    private int count;

    private long subtotal;

}


