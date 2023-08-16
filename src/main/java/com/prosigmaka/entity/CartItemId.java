package com.prosigmaka.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class CartItemId implements Serializable {

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Cart cart;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Product product;

}
