package com.prosigmaka.model;

import com.prosigmaka.entity.Cart;
import lombok.Getter;

@Getter
public class OrderResponse extends CartResponse {

    private final String paidStatus;
    private final String orderDate;

    public OrderResponse(Cart c) {
        super(c);
        this.paidStatus = c.getPaidStatus();
        this.orderDate = c.getOrderDate();
    }
}
