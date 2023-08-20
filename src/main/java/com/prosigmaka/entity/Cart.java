package com.prosigmaka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(
            name = "username",
            referencedColumnName = "username",
            nullable = false
    )
    private User user;

    private String orderDate;

    private long total;

    @Column(nullable = false)
    private String paidStatus;

    private String paymentMethod;

    private String deliveryMethod;

    @OneToMany(mappedBy = "cartItemId.cart")
    private List<CartItem> cartItems;

}
