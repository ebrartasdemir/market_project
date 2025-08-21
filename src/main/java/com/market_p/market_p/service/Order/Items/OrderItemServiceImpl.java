package com.market_p.market_p.service.Order.Items;

import com.market_p.market_p.entity.OrderItem;
import com.market_p.market_p.repository.OrderItemRepository;
import com.market_p.market_p.repository.OrderRepository;
import com.market_p.market_p.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderItem getOrderItemById(int id) {
       Optional<OrderItem> optionalOrderItem= orderItemRepository.findById(id);
       OrderItem orderItem=optionalOrderItem.orElse(null);
       if(orderItem==null){
           throw new RuntimeException("");
       }
        return orderItem;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderCode(String orderCode) {
        if(!orderRepository.existsByOrderCode(orderCode)){
            throw new RuntimeException("");
        }
        List<OrderItem> orderItemList=orderItemRepository.findByOrderOrderCode(orderCode);
        return orderItemList;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(int orderId) {
        if(!orderRepository.existsById(orderId)){
            throw new RuntimeException("");
        }
        List<OrderItem> orderItemList=orderItemRepository.findByOrderId(orderId);
        return orderItemList;

    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        if(!productRepository.existsById((orderItem.getProduct().getId()))){
            throw new RuntimeException("");
        }
        orderItemRepository.save(orderItem);
    }

    @Override
    public void updateOrderItem(int id,OrderItem newOrderItem) {
        Optional<OrderItem> optionalOrderItem=orderItemRepository.findById(id);
        OrderItem orderItem=optionalOrderItem.orElse(null);
        if(orderItem==null&&!productRepository.existsById((orderItem.getProduct().getId()))){
            throw new RuntimeException("");
        }
        if(newOrderItem.getPriceAtOrdersTime()!=0){
            orderItem.setPriceAtOrdersTime(newOrderItem.getPriceAtOrdersTime());
        }
        if (newOrderItem.getProduct()!=null) {
            orderItem.setProduct(newOrderItem.getProduct());
        }
        if (newOrderItem.getOrder()!=null) {
            orderItem.setOrder(newOrderItem.getOrder());
        }
        orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItemById(int id) {
        if(!orderItemRepository.existsById(id)){
            throw new RuntimeException("");
        }
        orderItemRepository.deleteById(id);
    }
}
