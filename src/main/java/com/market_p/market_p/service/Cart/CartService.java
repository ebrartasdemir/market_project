package com.market_p.market_p.service.Cart;

import com.market_p.market_p.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCart();
    Cart getCartById(int cartId);
    Cart getCartByUserId(int userId);
    void createCart(Cart cart);
    void updateCart(int id,Cart cart);
    void deleteCart(int cartId);
}
