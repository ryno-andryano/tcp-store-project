package com.prosigmaka.controller;

import com.prosigmaka.entity.Product;
import com.prosigmaka.model.ProductDto;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public ResponseEntity<ResponseEnvelope> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long pmin,
            @RequestParam(required = false) Long pmax
    ) {
        List<Product> products = productService.getAll(name, pmin, pmax);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, products), status);
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<ResponseEnvelope> getProduct(@PathVariable long id) {
        if (!productService.isExist(id)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Product not found"), status);
        }

        Product product = productService.get(id);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, product), status);
    }

    @PostMapping("/api/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseEnvelope> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.create(productDto);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new ResponseEnvelope(status, product), status);
    }

    @PutMapping("/api/product/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseEnvelope> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto) {
        if (!productService.isExist(id)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Product not found"), status);
        }

        Product product = productService.update(id, productDto);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, product), status);
    }

    @DeleteMapping("/api/product/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseEnvelope> deleteProduct(@PathVariable long id) {
        if (!productService.isExist(id)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Product not found"), status);
        }

        productService.delete(id);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, "Product deleted"), status);
    }

}
