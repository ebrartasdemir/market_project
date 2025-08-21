package com.market_p.market_p.dto.Cart.Item;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    @NotNull
    private int id;
    @NotNull
    private int productId;
    private double price;
    private int quantity;
}
