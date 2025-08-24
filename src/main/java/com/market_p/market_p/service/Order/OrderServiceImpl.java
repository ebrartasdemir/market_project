package com.market_p.market_p.service.Order;

import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;
import com.market_p.market_p.entity.*;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.OrderMapper;
import com.market_p.market_p.repository.*;
import com.market_p.market_p.repository.ProductRepository;
import com.market_p.market_p.utils.OrderUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AdressRepository adressRepository;
    private static final Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public List<OrderResDto> getAllOrders() {
        logger.info("[OrderService] Service Method: GetAllOrders - ( ) -  ------------");
        logger.info("[OrderService] Input: null");
        List<Order> orderList=orderRepository.findAll();
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        logger.info("[OrderService] Output: {}", orderResDtoList);
        logger.info("[OrderService] Listed Successfully.");
        return orderResDtoList;
    }

    @Override
    public List<OrderResDto> getAllOrdersByUser(int userId) {
        logger.info("[OrderService] Service Method: GetAllOrdersByUser - ( userId ) -  ------------");
        logger.info("[OrderService] Input : userId => {} " ,userId);
        List<Order>  orderList=orderRepository.findAllByUserId(userId);
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        logger.info("[OrderService] Output : {}", orderResDtoList);
        logger.info("[OrderService] Listed Successfully.");
        return orderResDtoList;
    }
    @Override
    public List<OrderResDto> getAllOrdersByUserAndStatus(int userId, Status status) {
        logger.info("[OrderService] Service Method: GetAllOrdersByUserAndStatus - ( userId , status ) - ------------");
        logger.info("[OrderService] Input : userId => {} status => {} ",userId,status);
        List<Order>  orderList=orderRepository.findAllByUserIdAndStatus(userId, status);
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        logger.info("[OrderService] Output : {}", orderResDtoList);
        logger.info("[OrderService] Listed Successfully.");
        return orderResDtoList;
    }
    @Override
    public List<OrderResDto> getAllOrdersByStatus( Status status) {
        logger.info("[OrderService] Service Method: GetAllOrdersByUserAndStatus - ( status ) - ------------");
        logger.info("[OrderService] Input: status => {} ",status);
        List<Order>  orderList=orderRepository.findAllByStatus(status);
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        logger.info("[OrderService] Output : {}", orderResDtoList);
        logger.info("[OrderService] Listed Successfully.");
        return orderResDtoList;
    }
    @Override
    public OrderResDto getOrderByOrderCode(String orderCode) {
        logger.info("[OrderService] Service Method: GetOrderByOrderCode - ( orderCode ) - ------------");
        logger.info("[OrderService] Input : orderCode => {} ",orderCode);
        Order order=orderRepository.findOrderByOrderCode(orderCode);
        OrderResDto orderResDto =orderMapper.orderToOrderDto(order);
        logger.info("[OrderService] Output : {}", orderResDto);
        logger.info("[OrderService] Listed Successfully.");
        return orderResDto;
    }

    @Override
    public OrderResDto getOrderById(int id) {
        logger.info("[OrderService] Service Method: GetOrderById - ( id ) - ------------]");
        logger.info("[OrderService] Input: id => {} ",id);
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            String message=String.format(Messages.Order.RECORD_NOT_FOUND,id);
            logger.error("[OrderService] Error : {}",message);
            throw new RuntimeException(message);
        }
        OrderResDto orderResDto =orderMapper.orderToOrderDto(order);
        logger.info("[OrderService] Output: id => {} ",id);
        return orderResDto;
    }

    @Override
    @Transactional
    public void createOrder(OrderReqDto orderReqDto) {
        logger.info("[OrderService] Service Method: createOrder - ( orderReqDto ) - ------------");
        logger.info("[OrderService] Input: orderReqDto => {} ",orderReqDto);
        Order order=orderMapper.OrderReqDtoToOrder(orderReqDto);
        List<CartItem> items=order.getCart().getCartItems();
        List<OrderItem> orderItems=new ArrayList<>();
        items.forEach(cartItem->{
            Product product= cartItem.getProduct();
            int newQuantity=product.getQuantity()-cartItem.getQuantity();
            if(newQuantity<0){
                String message=String.format(Messages.Product.RECORD_OUT_OF_STOCK);
                logger.error("[OrderService] Error : {}",message);
                throw new RuntimeException(message);
            }
            OrderItem orderItem=new  OrderItem(product,order,cartItem.getPrice(),cartItem.getQuantity());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
            product.setQuantity(newQuantity);
            productRepository.save(product);
            cartItemRepository.deleteById(cartItem.getId());
        });
        User user=userRepository.findById(orderReqDto.getUserId()).orElse(null);
        if(user==null){
            String message=String.format(Messages.User.RECORD_NOT_FOUND,orderReqDto.getUserId());
            logger.error("[OrderService] Error : {}",message);
            throw new RuntimeException(message);
        }
        order.setUser(user);
        order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getPriceAtOrdersTime).sum());
        order.setOrderItems(orderItems);
        if(order.getOrderCode()!=null){
            String message=String.format(Messages.Order.RECORD_NOT_FOUND_BY_ORDER_CODE,order.getOrderCode());
            logger.error("[OrderService] Error : {}",message);
            throw new RuntimeException(message);
        }
        Adress adress=adressRepository.findById(orderReqDto.getAdressId()).orElse(null);
        if(adress==null){
            String message=Messages.Adress.RECORD_NOT_FOUND;
            logger.error("[OrderService] Error : {}",message);
            throw  new RuntimeException(message);
        }
        order.setAdress(adress);
        order.setOrderCode(OrderUtils.orderCodeGenerator());
        logger.info("[OrderService] Output :null");
        logger.info("[OrderService] Created Successfully.");
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(int id, OrderReqDto orderReqDto) {
        logger.info("[OrderService] Service Method: updateOrder - ( id ) - ------------");
        logger.info("[OrderService] Input: id => {} ",id);
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            String message=String.format(Messages.Order.RECORD_NOT_FOUND,id);
            logger.error("[OrderService] Error : {}",message);
               throw new RuntimeException(message);
        }
        Order bodyOrder=orderMapper.OrderReqDtoToOrder(orderReqDto);
        if(bodyOrder.getOrderItems()!=null) {
            order.setOrderItems(bodyOrder.getOrderItems()) ;
            order.setTotalPrice(bodyOrder.getTotalPrice());
        }
        if(bodyOrder.getStatus()!=null) order.setStatus(bodyOrder.getStatus());
        if(bodyOrder.getUser()!=null){ order.setUser(bodyOrder.getUser());
            order.setCart(bodyOrder.getCart());}
        if(bodyOrder.getAdress()!=null) order.setAdress(bodyOrder.getAdress());
        logger.info("[OrderService] Output :null");
        logger.info("[OrderService] Updated Successfully.");
        orderRepository.save(order);
    }

    @Override
    public void updateStatusOrder(int id, Status status) {
        logger.info("[OrderService] Service Method: updateStatusOrder - ( id, status ) - ------------");
        logger.info("[OrderService] Input: id => {} status => {}",id,status);
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            String message=String.format(Messages.Order.RECORD_NOT_FOUND,id);
            logger.error("[OrderService] Error : {}",message);
            throw new RuntimeException(message);
        }
        order.setStatus(status);
        logger.info("[OrderService] Output :null");
        logger.info("[OrderService] Updated Successfully.");
        orderRepository.save(order);
    }


    @Override
    public void deleteOrder(int id) {
        logger.info("[OrderService] Service Method: deleteOrder - ( id ) ------------");
        logger.info("[OrderService] Input: id => {} ",id);
        if(orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else{
            String message=String.format(Messages.Order.RECORD_NOT_FOUND,id);
            logger.error("[OrderService] Error : {}",message);
            throw new RuntimeException(message);
        }
    }
}
