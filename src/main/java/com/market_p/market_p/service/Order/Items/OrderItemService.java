package com.market_p.market_p.service.Order.Items;

import com.market_p.market_p.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem getOrderItemById(int id);
    List<OrderItem> getOrderItemByOrderCode(String orderCode);
    List<OrderItem> getOrderItemByOrderId(int orderId);
    void createOrderItem(OrderItem orderItem);
    void updateOrderItem(int id,OrderItem orderItem);
    void deleteOrderItemById(int id);
}
