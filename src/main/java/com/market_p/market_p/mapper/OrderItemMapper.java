package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Order.Item.OrderItemDto;
import com.market_p.market_p.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemMapper {
    public OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPriceAtOrdersTime(orderItem.getPriceAtOrdersTime());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        return orderItemDto;
    }
    public List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> orderItemList) {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            orderItemDtoList.add(orderItemToOrderItemDto(orderItem));
        }
        return orderItemDtoList;
    }
}
