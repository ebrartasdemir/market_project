package com.market_p.market_p.dto.Cart;

import com.market_p.market_p.dto.Cart.Item.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private double totalPrice;
    private List<CartItemDto> cartItems;
}
