package com.market_p.market_p.dto.Cart;

import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartAdminDto {
    private int userId;
    private int cartId;
    private double totalPrice;
    private List<CartItemResDto> cartItems;
}
