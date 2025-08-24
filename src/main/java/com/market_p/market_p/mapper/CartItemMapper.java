package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import com.market_p.market_p.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemMapper {
    public CartItem cartItemDtoToCart(CartItemReqDto cartItemReqDto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemReqDto.getQuantity());
        return cartItem;
    }
    public CartItemResDto cartItemToCartItemResDto(CartItem cartItem) {
        CartItemResDto cartItemResDto = new CartItemResDto();
        cartItemResDto.setId(cartItem.getId());
        cartItemResDto.setQuantity(cartItem.getQuantity());
        cartItemResDto.setProductId(cartItem.getProduct().getId());
        cartItemResDto.setProductName(cartItem.getProduct().getName());
        cartItemResDto.setPrice(cartItem.getProduct().getPrice());
        return cartItemResDto;
    }
    public List<CartItemResDto> cartItemListToCartItemResDtoList(List<CartItem> cartItemList) {
        List<CartItemResDto> cartItemResDtoList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            cartItemResDtoList.add(cartItemToCartItemResDto(cartItem));
        }
        return cartItemResDtoList;
    }
}
