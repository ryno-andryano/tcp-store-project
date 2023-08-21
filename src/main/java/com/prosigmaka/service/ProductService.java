package com.prosigmaka.service;

import com.prosigmaka.entity.Product;

import java.util.List;

public interface ProductService {

    boolean isExist(long id);

    List<Product> getAll(String name, Long pmin, Long pmax);

    Product get(long id);

    Product create(Product reqProduct);

    Product update(long id, Product reqProduct);

    void delete(long id);
}
