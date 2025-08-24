package com.market_p.market_p.service.Cart.Items;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;

import java.util.List;

public interface CartItemService {
    List<CartItemResDto> getAllCartItems();
    List<CartItemResDto> getCartItemsByUserId(int userId);
    List<CartItemResDto> getCartItemByCartId(int cartId);
    CartItemResDto getCartItemById(int id);
    void createCartItem(CartItemReqDto cartItemReqDto);
    void updateCartItem(int id, CartItemReqDto cartItemReqDto);
    void updateQuantityCartItem(int id,int quantity);
    void deleteCartItem(int id);
}
