package com.market_p.market_p.repository;

import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUserId(int userId);

    boolean existsByUserId(int userId);

    boolean deleteByUserId(int userId);

    int user(User user);
}
