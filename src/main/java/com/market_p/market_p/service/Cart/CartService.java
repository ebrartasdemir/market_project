package com.market_p.market_p.service.Cart;

import com.market_p.market_p.dto.Cart.CartAdminDto;
import com.market_p.market_p.dto.Cart.CartDto;
import com.market_p.market_p.entity.Cart;

import java.util.List;

public interface CartService {
    List<CartAdminDto> getAllCart();
    CartAdminDto getCartById(int cartId);
    CartDto getCartByUserId(int userId);
    void createCart(int userId);
    void updateCart(int userId, Cart cart);
    void deleteCart(int userId);
}
