package com.prosigmaka.service;

import com.prosigmaka.entity.Product;
import com.prosigmaka.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(long id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll(String name, Long pmin, Long pmax) {
        return productRepository.findAllByNameLikeAndPriceGreaterThanEqualAndPriceLessThanEqual(name, pmin, pmax);
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Product create(Product reqProduct) {
        Product product = new Product();
        product.setName(reqProduct.getName());
        product.setDescription(reqProduct.getDescription());
        product.setPrice(reqProduct.getPrice());
        product.setImage(reqProduct.getImage());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(long id, Product reqProduct) {
        Product product = get(id);

        if (reqProduct.getName() != null) {
            product.setName(reqProduct.getName());
        }
        if (reqProduct.getDescription() != null) {
            product.setDescription(reqProduct.getDescription());
        }
        if (reqProduct.getPrice() != 0) {
            product.setPrice(reqProduct.getPrice());
        }
        if (reqProduct.getImage() != null) {
            product.setImage(reqProduct.getImage());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
