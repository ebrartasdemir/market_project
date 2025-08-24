package com.market_p.market_p.repository;

import com.market_p.market_p.entity.Order;
import com.market_p.market_p.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserId(int userId);
    List<Order> findAllByUserIdAndStatus(int userId, Status status);
    Order findOrderByOrderCode(String orderCode);

    boolean existsByOrderCode(String orderCode);

    List<Order> findAllByStatus(Status status);
}
