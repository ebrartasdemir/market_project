package com.market_p.market_p.dto.Cart.Item;


import com.market_p.market_p.example.constants.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReqDto {
    @NotNull(message = Messages.Product.ID_CANT_BE_EMPTY)
    private int productId;
    private int quantity=1;
    @NotNull(message = Messages.Cart.ID_CANT_BE_EMPTY)
    private int cartId;
}
