package com.market_p.market_p.service.Order;

import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;
import com.market_p.market_p.entity.*;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.OrderMapper;
import com.market_p.market_p.repository.*;
import com.market_p.market_p.repository.ProductRepository;
import com.market_p.market_p.utils.OrderUtils;
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


    @Override
    public List<OrderResDto> getAllOrders() {
        List<Order> orderList=orderRepository.findAll();
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        return orderResDtoList;
    }

    @Override
    public List<OrderResDto> getAllOrdersByUser(int userId) {
        List<Order>  orderList=orderRepository.findAllByUser(userId);
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        return orderResDtoList;
    }
    public List<OrderResDto> getAllOrdersByUserAndStatus(int userId, Status status) {
        List<Order>  orderList=orderRepository.findAllByUserAndStatus(userId, status);
        List<OrderResDto> orderResDtoList =orderMapper.orderToOrderDtoList(orderList);
        return orderResDtoList;
    }
    @Override
    public List<OrderResDto> getAllPaidOrdersByUser(int userId) {
        List<OrderResDto> orderResDtoList =getAllOrdersByUserAndStatus(userId, Status.PAID);
        return orderResDtoList;
    }

    @Override
    public List<OrderResDto> getAllInTransitOrdersByUser(int userId) {
        List<OrderResDto> orderResDtoList =getAllOrdersByUserAndStatus(userId, Status.INTRANSPORTATION);
        return orderResDtoList;
    }

    @Override
    public List<OrderResDto> getAllCompletedOrdersByUser(int userId) {
        List<OrderResDto> orderResDtoList =getAllOrdersByUserAndStatus(userId, Status.DONE);
        return orderResDtoList;
    }

    @Override
    public OrderResDto getOrderByOrderCode(String orderCode) {
        Order order=orderRepository.findOrderByOrderCode(orderCode);
        OrderResDto orderResDto =orderMapper.orderToOrderDto(order);
        return orderResDto;
    }


    @Override
    public OrderResDto getOrderById(int id) {
        Order order=orderRepository.getById(id);
        OrderResDto orderResDto =orderMapper.orderToOrderDto(order);
        return orderResDto;
    }

    @Override
    public void createOrder(OrderReqDto orderReqDto) {
        Order order=orderMapper.OrderReqDtoToOrder(orderReqDto);

        List<CartItem> items=order.getCart().getCartItems();
        List<OrderItem> orderItems=new ArrayList<>();
        items.forEach(cartItem->{
            Product product= cartItem.getProduct();
            int newQuantity=product.getQuantity()-cartItem.getQuantity();
            if(newQuantity<0){
                throw new RuntimeException(Messages.Product.RECORD_OUT_OF_STOCK);
            }
            OrderItem orderItem=new  OrderItem(product,order,cartItem.getPrice(),cartItem.getQuantity());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
            product.setQuantity(newQuantity);
            productRepository.save(product);
            cartItemRepository.deleteById(cartItem.getId());
        });
        User user=userRepository.findById(orderReqDto.getUserId()).orElse(null);
        if(user==null){ throw new RuntimeException(String.format(Messages.User.RECORD_NOT_FOUND,orderReqDto.getUserId()));}
        order.setUser(user);
        order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getPriceAtOrdersTime).sum());
        order.setOrderItems(orderItems);
        if(order.getOrderCode()!=null){
            throw new RuntimeException();
        }
        Adress adress=adressRepository.findById(orderReqDto.getAdressId()).orElse(null);
        if(adress==null){ throw  new RuntimeException(Messages.Adress.RECORD_NOT_FOUND);}
        order.setAdress(adress);
        order.setOrderCode(OrderUtils.orderCodeGenerator());
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(int id, OrderReqDto orderReqDto) {
        Order order=orderRepository.getById(id);
        if(order==null){
               throw new RuntimeException(Messages.EMPTY_BODY);
        }
        Order bodyOrder=orderMapper.OrderReqDtoToOrder(orderReqDto);
        if(bodyOrder.getOrderItems()!=null) {
            order.setOrderItems(bodyOrder.getOrderItems()) ;
            order.setTotalPrice(bodyOrder.getTotalPrice());}
        if(bodyOrder.getStatus()!=null) order.setStatus(bodyOrder.getStatus());
        if(bodyOrder.getUser()!=null){ order.setUser(bodyOrder.getUser());
            order.setCart(bodyOrder.getCart());}
        if(bodyOrder.getAdress()!=null) order.setAdress(bodyOrder.getAdress());
        orderRepository.save(order);
    }

    @Override
    public void updateStatusOrder(int id, Status status) {
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            throw new RuntimeException(String.format(Messages.Order.RECORD_NOT_FOUND,id));
        }
        order.setStatus(status);
        orderRepository.save(order);
    }


    @Override
    public void deleteOrder(int id) {
        if(orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else throw new RuntimeException(Messages.Order.RECORD_NOT_FOUND);
    }
}
