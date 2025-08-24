package com.market_p.market_p.dto.Order.Item;

import com.market_p.market_p.entity.Order;
import com.market_p.market_p.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int id;
    private int productId;
    private String productName;
    private double priceAtOrdersTime;
    private int quantity;
}
