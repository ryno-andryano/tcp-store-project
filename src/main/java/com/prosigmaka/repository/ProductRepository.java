package com.prosigmaka.repository;

import com.prosigmaka.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
           "(:name is null or p.name LIKE %:name%) AND " +
           "(:pmin is null or p.price >= :pmin) AND " +
           "(:pmax is null or p.price <= :pmax)")
    List<Product> findAllByNameLikeAndPriceGreaterThanEqualAndPriceLessThanEqual(
            @Param("name") String name, @Param("pmin") Long pmin, @Param("pmax") Long pmax
    );

    List<Product> findFirst3ByOrderByIdDesc();
}
