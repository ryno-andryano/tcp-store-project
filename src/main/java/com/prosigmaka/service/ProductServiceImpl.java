package com.prosigmaka.service;

import com.prosigmaka.entity.Product;
import com.prosigmaka.model.ProductDto;
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
    public Product create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(long id, ProductDto productDto) {
        Product product = get(id);
        
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getImage() != null) {
            product.setImage(productDto.getImage());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
