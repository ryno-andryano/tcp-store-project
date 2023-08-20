package com.prosigmaka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartItemId implements Serializable {

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Product product;

}
