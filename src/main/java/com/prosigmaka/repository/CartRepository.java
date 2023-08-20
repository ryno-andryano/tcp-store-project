package com.prosigmaka.repository;

import com.prosigmaka.entity.Cart;
import com.prosigmaka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByUserAndPaidStatus(User user, String paidStatus);

    Cart findByUserAndPaidStatus(User user, String paidStatus);

    List<Cart> findAllByUserAndPaidStatus(User user, String paidStatus);
}
