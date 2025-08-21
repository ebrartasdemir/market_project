package com.market_p.market_p.repository;

import com.market_p.market_p.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    List<CartItem> getCartItemsByCartUserId(int userId);

    List<CartItem> getCartItemsByCartId(int cartId);
}
