package com.market_p.market_p.service.Order;

import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;
import com.market_p.market_p.entity.Status;

import java.util.List;

public interface OrderService {
    List<OrderResDto> getAllOrders();
    List<OrderResDto> getAllOrdersByUser(int userId);
    List<OrderResDto> getAllPaidOrdersByUser(int userId);
    List<OrderResDto> getAllInTransitOrdersByUser(int userId);
    List<OrderResDto> getAllCompletedOrdersByUser(int userId);
    OrderResDto getOrderByOrderCode(String orderCode);
    OrderResDto getOrderById(int id);
    void createOrder(OrderReqDto orderReqDto);
    void updateOrder(int id, OrderReqDto orderReqDto);
    void updateStatusOrder(int id, Status status);
    void deleteOrder(int id);
}
