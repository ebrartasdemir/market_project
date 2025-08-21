package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;

import com.market_p.market_p.entity.Status;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Order.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.market_p.market_p.utils.Jsonify.toJson;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllOrders(){
        logger.info("Api: GET=> /orders");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrders();
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);        }
    }
    @GetMapping("/orders/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllOrdersByUserId(@PathVariable int userId){
        logger.info("Api: GET=> /orders/{}",userId);
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByUser(userId);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/paid/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllPaidOrdersByUserId(@PathVariable int userId){
        logger.info("Api: GET=> /orders/paid/{}",userId);
        try{
            List<OrderResDto> orderList=  orderService.getAllPaidOrdersByUser(userId);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/intransit/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllInTransitOrdersByUserId(@PathVariable int userId){
        logger.info("Api: GET=> /orders/instransit/{}",userId);
        try{
            List<OrderResDto> orderList=  orderService.getAllInTransitOrdersByUser(userId);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/complete/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllCompleteOrdersByUserId(@PathVariable int userId){
        logger.info("Api: GET=> /orders/complete/{}",userId);
        try{
            List<OrderResDto> orderList=  orderService.getAllCompletedOrdersByUser(userId);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{orderCode}")
    public ResponseEntity<ApiResponse<OrderResDto>> getOrderByOrderCode(@PathVariable String orderCode) {
        logger.info("Api: GET=>/order/{}", orderCode);
        try {
            OrderResDto orderResDto = orderService.getOrderByOrderCode(orderCode);
            String message = Messages.Order.RECORD_FOUND_BY_ORDER_CODE;
            if (orderResDto == null) message =Messages.Order.RECORD_NOT_FOUND_BY_ORDER_CODE;
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(String.format(message, orderCode), orderResDto);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>( String.format(Messages.Order.RECORD_NOT_FOUND_BY_ORDER_CODE, orderCode) + e.getMessage());
            logger.error("Error Response Body: {}", toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse<OrderResDto>> getOrderByOrderCode(@PathVariable @Min(1) int  id) {
        logger.info("Api: GET=>/order/{}", id);
        try {
            OrderResDto orderResDto = orderService.getOrderById(id);
            String message = Messages.Order.RECORD_FOUND;
            if (orderResDto == null) message = Messages.Order.RECORD_NOT_FOUND;
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(String.format(message, id), orderResDto);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_ERROR + e.getMessage());
            logger.error("Error Response Body: {}", toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<String>> addOrder(@Valid @RequestBody OrderReqDto order){
        logger.info("Api: POST=> /product", order.toString());
        logger.info("Request Body: {}", toJson(order));
        try{
            orderService.createOrder(order);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Order.RECORD_CREATED);
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_CREATED +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable @Min(1) int id,@Valid @RequestBody OrderReqDto order){
        logger.info("Api: PUT=> /order/{id}", id);
        logger.info("Request Body: {}", toJson(order));
        try{
            orderService.updateOrder(id, order);
            String message=Messages.Order.RECORD_UPDATED;
            if(order == null) message=Messages.Order.RECORD_NOT_FOUND;
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/paid/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatustoPaid(@PathVariable @Min(1) int id){
        logger.info("Api: PUT=> /order/paid/{id}", id);
        try{
            orderService.updateStatusOrder(id,Status.PAID);
            String message=String.format(Messages.Order.RECORD_UPDATED,"paid");
            //if(order == null) message="Messages.Product.RECORD_NOT_FOUND";
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/intransit/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatustoIntransit(@PathVariable @Min(1) int id){
        logger.info("Api: PUT=> /order/intransit/{id}", id);
        try{
            orderService.updateStatusOrder(id,Status.INTRANSPORTATION);
            String message=String.format(Messages.Order.RECORD_UPDATED,"intransit");
            //if(order == null) message="Messages.Product.RECORD_NOT_FOUND";
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/completed/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatusToComplete(@PathVariable @Min(1) int id){
        logger.info("Api: PUT=> /order/completed/{id}", id);
        try{
            orderService.updateStatusOrder(id, Status.DONE);
            String message=String.format(Messages.Order.RECORD_UPDATED,"complete");
            //if(order == null) message="Messages.Product.RECORD_NOT_FOUND";
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @DeleteMapping("/order/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable @Min(1) int id){
        logger.info("Api: DELETE=> /order/{id}", id);
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(String.format(Messages.Order.RECORD_DELETED_ERROR,id) +e.getMessage());
            logger.error("Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

}
