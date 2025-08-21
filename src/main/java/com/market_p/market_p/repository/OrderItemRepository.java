package com.market_p.market_p.repository;

import com.market_p.market_p.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderCode(String orderCode);
    List<OrderItem> findByOrderId(int orderId);
}
