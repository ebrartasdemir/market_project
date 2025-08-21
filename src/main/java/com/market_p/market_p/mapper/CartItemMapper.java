package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.CartItem;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.repository.CartRepository;
import com.market_p.market_p.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {
    public CartItem cartItemDtoToCart(CartItemReqDto cartItemReqDto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemReqDto.getQuantity());
        return cartItem;
    }
}
