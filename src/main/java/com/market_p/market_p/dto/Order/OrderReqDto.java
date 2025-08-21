package com.market_p.market_p.dto.Order;
import com.market_p.market_p.entity.Adress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
    private String orderCode;
    private int userId;
    private Date orderDate;
    private int adressId;
    private List<Integer> orderItemsIds;
}
