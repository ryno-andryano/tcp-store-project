package com.prosigmaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @OneToMany(mappedBy = "cartItemId.cart")
    private List<CartItem> cartItems;

    private long total;

    @Column(nullable = false)
    private String paymentMethod = "NOT_SET";

    @Column(nullable = false)
    private String deliveryMethod = "NOT_SET";

    @Column(nullable = false)
    private String paidStatus = "PENDING";

    private String orderDate;

}
