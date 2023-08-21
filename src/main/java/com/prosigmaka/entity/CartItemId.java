package com.prosigmaka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
