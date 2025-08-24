package com.market_p.market_p.service.Order.Items;

import com.market_p.market_p.dto.Order.Item.OrderItemDto;
import com.market_p.market_p.entity.OrderItem;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.OrderItemMapper;
import com.market_p.market_p.mapper.OrderMapper;
import com.market_p.market_p.repository.OrderItemRepository;
import com.market_p.market_p.repository.OrderRepository;
import com.market_p.market_p.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private OrderMapper orderMapper;
    private static final Logger logger= LoggerFactory.getLogger(OrderItemServiceImpl.class);
    @Autowired
    private OrderItemMapper orderItemMapper;
    private String message;

    @Override
    public List<OrderItemDto> getAllOrderItems(){
        logger.info("[OrderItemService] Service Method: getAllOrderItems - ( ) - ------------");
        logger.info("[OrderItemService] Input: null");
        List<OrderItem> orderItemList=orderItemRepository.findAll();
        List<OrderItemDto> orderItemDtoList= orderItemMapper.orderItemListToOrderItemDtoList(orderItemList);
        logger.info("[OrderItemService] Output : {}", orderItemDtoList);
        logger.info("[OrderItemService] Listed Successfully.");
        return orderItemDtoList;
    }

    @Override
    public OrderItemDto getOrderItemById(int id) {
        logger.info("[OrderItemService] Service Method: getOrderItemById - ( id ) - ------------");
        logger.info("[OrderItemService] Input: id => {} ",id);
        OrderItem orderItem=orderItemRepository.findById(id).orElse(null);
        if(orderItem==null){
            message= String.format(Messages.Order.Items.RECORD_NOT_FOUND,id);
            logger.error("[OrderItemService] Error: {}",message);
        }
        OrderItemDto orderItemDto=orderItemMapper.orderItemToOrderItemDto(orderItem);
        logger.info("[OrderItemService] Output : DTO => {}",orderItemDto);
        logger.info("[OrderItemService] Found Successfully.");
        return orderItemDto;
    }

    @Override
    public List<OrderItemDto> getOrderItemByOrderCode(String orderCode) {
        logger.info("[OrderItemService] Service Method: getOrderItemByOrderCode - ( orderCode ) - ------------");
        logger.info("[OrderItemService] Input: orderCode => {} ",orderCode);
        if(!orderRepository.existsByOrderCode(orderCode)){
            message= String.format(Messages.Order.RECORD_FOUND_BY_ORDER_CODE,orderCode);
            logger.error("[OrderItemService] Error: {}",message);
            throw new RuntimeException(message);
        }
        List<OrderItem> orderItemList=orderItemRepository.findByOrderOrderCode(orderCode);
        List<OrderItemDto> orderItemDtoList=orderItemMapper.orderItemListToOrderItemDtoList(orderItemList);
        logger.info("[OrderItemService] Output : DTO => {}",orderItemDtoList);
        logger.info("[OrderItemService] Listed Successfully.");
        return orderItemDtoList;
    }

    @Override
    public List<OrderItemDto> getOrderItemByOrderId(int orderId) {
        logger.info("[OrderItemService] Service Method: getOrderItemByOrderId - ( orderId ) - ------------");
        logger.info("[OrderItemService] Input: orderId => {} ",orderId);
        if(!orderRepository.existsById(orderId)){
            message= String.format(Messages.Order.RECORD_NOT_FOUND,orderId);
            logger.error("[OrderItemService] Error: {}",message);
            throw new RuntimeException(message);
        }
        List<OrderItem> orderItemList=orderItemRepository.findByOrderId(orderId);
        List<OrderItemDto> orderItemDtoList=orderItemMapper.orderItemListToOrderItemDtoList(orderItemList);
        logger.info("[OrderItemService] Output : DTO => {}",orderItemDtoList);
        logger.info("[OrderItemService] Listed Successfully.");
        return orderItemDtoList;
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        logger.info("[OrderItemService] Service Method: createOrderItem - ( orderItem ) - ------------");
        logger.info("[OrderItemService] Input: orderItem => {} ",orderItem);
        if(!productRepository.existsById((orderItem.getProduct().getId()))){
            message=String.format(Messages.Product.RECORD_NOT_FOUND,orderItem.getProduct().getId());
            logger.error("[OrderItemService] Error: {}",message);
            throw new RuntimeException(message);
        }
        orderItemRepository.save(orderItem);
        logger.info("[OrderItemService] Output : null");
        logger.info("[OrderItemService] Found Successfully.");
    }

    @Override
    public void updateOrderItem(int id,OrderItem newOrderItem) {
        logger.info("[OrderItemService] Service Method: updateOrderItem - ( id , orderItem ) - ------------");
        logger.info("[OrderItemService] Input: id => {} orderItem => {} ",id,newOrderItem);
        Optional<OrderItem> optionalOrderItem=orderItemRepository.findById(id);
        OrderItem orderItem=optionalOrderItem.orElse(null);
        if(orderItem==null&&!productRepository.existsById((orderItem.getProduct().getId()))){
            message=String.format(Messages.Product.RECORD_NOT_FOUND,orderItem.getProduct().getId());
            logger.error("[OrderItemService] Error: {}",message);
            throw new RuntimeException(message);
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
        logger.info("[OrderItemService] Output : null");
        logger.info("[OrderItemService] Created Successfully.");
    }

    @Override
    public void deleteOrderItemById(int id) {
        logger.info("[OrderItemService] Service Method: deleteOrderItemById - ( id ) - ------------");
        logger.info("[OrderItemService] Input: id => {}",id);
        if(!orderItemRepository.existsById(id)){
            message=String.format(Messages.Product.RECORD_NOT_FOUND,id);
            logger.error("[OrderItemService] Error: {}",message);
            throw new RuntimeException(message);
        }
        orderItemRepository.deleteById(id);
        logger.info("[OrderItemService] Output : null");
        logger.info("[OrderItemService] Deleted Successfully.");
    }
}
