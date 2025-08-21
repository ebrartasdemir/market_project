package com.market_p.market_p.repository;

import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart getByUserId(int userId);

    Cart findByUserId(int userId);

    boolean existsByUserId(int userId);
}
