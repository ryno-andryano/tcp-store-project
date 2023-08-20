package com.prosigmaka.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId;

    private int count;

    private long subtotal;

}


