package com.prosigmaka.service;

import com.prosigmaka.entity.Product;
import com.prosigmaka.model.ProductDto;

import java.util.List;

public interface ProductService {

    boolean isExist(long id);

    List<Product> getAll(String name, Long pmin, Long pmax);

    Product get(long id);

    Product create(ProductDto productDto);

    Product update(long id, ProductDto productDto);

    void delete(long id);
}
