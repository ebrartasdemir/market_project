package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;
import com.market_p.market_p.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResDto orderToOrderDto(Order order){
        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setId(order.getId());
        orderResDto.setStatus(order.getStatus());
        orderResDto.setOrderCode(order.getOrderCode());
        orderResDto.setOrderDate(order.getOrderDate());
        orderResDto.setOrderItemList(order.getOrderItems());
        orderResDto.setPrice(order.getTotalPrice());
        return orderResDto;
    }
    public List<OrderResDto> orderToOrderDtoList(List<Order> orderList){
        List<OrderResDto> orderResDtoList = new ArrayList<>();
        orderList.forEach(order -> {
            orderResDtoList.add(orderToOrderDto(order));});
        return orderResDtoList;
    }
    public Order  OrderReqDtoToOrder(OrderReqDto orderReqDto){
        Order order = new Order();
        order.setOrderCode(orderReqDto.getOrderCode());
        order.setOrderDate(orderReqDto.getOrderDate());
        return order;
    }
}
