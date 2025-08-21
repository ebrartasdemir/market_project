package com.market_p.market_p.service.Cart.Items;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.entity.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> getAllCartItems();
    List<CartItem> getCartItemsByUserId(int userId);
    List<CartItem> getCartItemByCartId(int cartId);
    CartItem getCartItemById(int id);
    void createCartItem(CartItemReqDto cartItemReqDto);
    void updateCartItem(int id, CartItemReqDto cartItemReqDto);
    void updateQuantityCartItem(int id,int quantity);
    void deleteCartItem(int id);
}
