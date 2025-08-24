package com.market_p.market_p.service.Order.Items;

import com.market_p.market_p.dto.Order.Item.OrderItemDto;
import com.market_p.market_p.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems();

    OrderItemDto getOrderItemById(int id);
    List<OrderItemDto> getOrderItemByOrderCode(String orderCode);
    List<OrderItemDto> getOrderItemByOrderId(int orderId);
    void createOrderItem(OrderItem orderItem);
    void updateOrderItem(int id,OrderItem orderItem);
    void deleteOrderItemById(int id);
}
