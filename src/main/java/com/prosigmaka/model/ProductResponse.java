package com.prosigmaka.model;

import com.prosigmaka.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final long id;
    private final String name;
    private final long price;
    private final String image;

    public ProductResponse(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
        if (!p.getImage().isEmpty()) this.image = p.getImage().get(0);
        else this.image = null;
    }
}
