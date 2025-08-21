package com.market_p.market_p.dto.Order;
import com.market_p.market_p.entity.OrderItem;
import com.market_p.market_p.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResDto {
    private int id;
    private String orderCode;
    private Status status;
    private Date orderDate;
    private double price;
    private String adress;
    private List<OrderItem> orderItemList;
}

