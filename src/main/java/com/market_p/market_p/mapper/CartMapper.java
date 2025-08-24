package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Cart.CartAdminDto;
import com.market_p.market_p.dto.Cart.CartDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import com.market_p.market_p.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {
    public CartDto cartToCartDto(List<CartItemResDto> cartItemResDtoList) {
        CartDto cartDto=new CartDto(
                cartItemResDtoList,
                cartItemResDtoList.stream().mapToDouble(CartItemResDto::getPrice).sum()
        );
        return cartDto;
    }
    public CartAdminDto cartToCartAdminDto(Cart cart,List<CartItemResDto> cartItemResDtoList) {
        CartAdminDto cartAdminDto=new CartAdminDto(
                cart.getUser().getId(),
                cart.getId(),
                cartItemResDtoList.stream().mapToDouble(CartItemResDto::getPrice).sum(),
                cartItemResDtoList
        );
        return cartAdminDto;
    }
}
