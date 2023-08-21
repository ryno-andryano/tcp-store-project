package com.prosigmaka.model;

import com.prosigmaka.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final String name;
    private final long price;
    private final String image;

    public ProductResponse(Product p) {
        this.name = p.getName();
        this.price = p.getPrice();
        this.image = p.getImage();
    }
}
