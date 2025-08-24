package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Order.OrderReqDto;
import com.market_p.market_p.dto.Order.OrderResDto;

import com.market_p.market_p.entity.Status;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Order.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        logger.info("[OrderController] Api: GET=> /orders");
        logger.info("[OrderController] Request Body: null");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrders();
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);        }
    }
    @GetMapping("/orders/user")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllOrdersByUserId(@AuthenticationPrincipal User user){
        int userId = user.getId();
        logger.info("[OrderController] Api: GET=> /orders/user");
        logger.info("[OrderController] Request Body: null");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByUser(userId);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/paid")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllPaidOrders(){
        logger.info("[OrderController] Api: GET=> /orders/paid");
        logger.info("[OrderController] Request Body: null");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByStatus(Status.PAID);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/intransit")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllIntransitOrders(){
        logger.info("[OrderController] Api: GET=> /orders/intransit");
        logger.info("[OrderController] Request Body: null");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByStatus(Status.INTRANSPORTATION);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/complete")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllCompleteOrders(){
        logger.info("[OrderController] Api: GET=> /orders/paid");
        logger.info("[OrderController] Request Body: null");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByStatus(Status.DONE);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/paid/user")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllPaidOrdersByUserId(@AuthenticationPrincipal User user){
        int userId = user.getId();
        logger.info("[OrderController] Api: GET=> /orders/instransit/user");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByUserAndStatus(userId,Status.PAID);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/intransit/user")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllInTransitOrdersByUserId(@AuthenticationPrincipal User user){
        int userId = user.getId();
        logger.info("[OrderController] Api: GET=> /orders/instransit/user");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByUserAndStatus(userId,Status.INTRANSPORTATION);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orders/complete/user")
    public ResponseEntity<ApiResponse<List<OrderResDto>>> getAllCompleteOrdersByUserId(@AuthenticationPrincipal User user){
        int userId = user.getId();
        logger.info("[OrderController] Api: GET=> /orders/complete/user");
        try{
            List<OrderResDto> orderList=  orderService.getAllOrdersByUserAndStatus(userId,Status.DONE);
            ApiResponse<List<OrderResDto>> apiResponse = new ApiResponse<>(Messages.Order.RECORDS_FOUND_AND_LISTED, orderList);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<OrderResDto>> apiResponse= new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{orderCode}")
    public ResponseEntity<ApiResponse<OrderResDto>> getOrderByOrderCode(@PathVariable String orderCode) {
        logger.info("[OrderController] Api: GET=>/order/{}", orderCode);
        try {
            OrderResDto orderResDto = orderService.getOrderByOrderCode(orderCode);
            String message = Messages.Order.RECORD_FOUND_BY_ORDER_CODE;
            if (orderResDto == null) message =Messages.Order.RECORD_NOT_FOUND_BY_ORDER_CODE;
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(String.format(message, orderCode), orderResDto);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Found Successfully.");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>( String.format(Messages.Order.RECORD_NOT_FOUND_BY_ORDER_CODE, orderCode) + e.getMessage());
            logger.error("[OrderController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse<OrderResDto>> getOrderById(@PathVariable @Min(1) int  id) {
        logger.info("[OrderController] Api: GET=>/order/{}", id);
        logger.info("[OrderController] Request Body: null");
        try {
            OrderResDto orderResDto = orderService.getOrderById(id);
            String message = Messages.Order.RECORD_FOUND;
            if (orderResDto == null) message = Messages.Order.RECORD_NOT_FOUND;
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(String.format(message, id), orderResDto);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Found Successfully.");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(Messages.Order.RECORD_NOT_FOUND_ERROR + e.getMessage());
            logger.error("[OrderController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[OrderController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<String>> addOrder(@Valid @RequestBody OrderReqDto order){
        logger.info("[OrderController] Api: POST=> /product");
        logger.info("[OrderController] Request Body: {}", toJson(order));
        try{
            orderService.createOrder(order);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Order.RECORD_CREATED);
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Created Successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_CREATED +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Create Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable @Min(1) int id,@Valid @RequestBody OrderReqDto order){
        logger.info("[OrderController] Api: PUT=> /order/{id}", id);
        logger.info("[OrderController] Request Body: {}", toJson(order));
        try{
            orderService.updateOrder(id, order);
            String message=Messages.Order.RECORD_UPDATED;
            if(order == null) message=Messages.Order.RECORD_NOT_FOUND;
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Updated Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Update Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/paid/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatustoPaid(@PathVariable @Min(1) int id){
        logger.info("[OrderController] Api: PUT=> /order/paid/{id}", id);
        logger.info("[OrderController] Request Body: null");

        try{
            orderService.updateStatusOrder(id,Status.PAID);
            String message=String.format(Messages.Order.RECORD_UPDATE_STATUS,"paid");
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Updated Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Update Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/intransit/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatustoIntransit(@PathVariable @Min(1) int id){
        logger.info("[OrderController] Api: PUT=> /order/intransit/{id}", id);
        logger.info("[OrderController] Request Body: null");
        try{
            orderService.updateStatusOrder(id,Status.INTRANSPORTATION);
            String message=String.format(Messages.Order.RECORD_UPDATE_STATUS,"intransit");
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Updated Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Update Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/order/completed/{id}")
    public ResponseEntity<ApiResponse<String>> updateProductStatusToComplete(@PathVariable @Min(1) int id){
        logger.info("[OrderController] Api: PUT=> /order/completed/{id}", id);
        logger.info("[OrderController] Request Body: null");

        try{
            orderService.updateStatusOrder(id, Status.DONE);
            String message=String.format(Messages.Order.RECORD_UPDATE_STATUS,"complete");
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            logger.info("[OrderController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderController] Updated Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Order.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Update Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @DeleteMapping("/order/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable @Min(1) int id){
        logger.info("[OrderController] Api: DELETE=> /order/{id}", id);
        logger.info("[OrderController] Request Body: null");
        try{
            orderService.deleteOrder(id);
            logger.info("[OrderController] Deleted Successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(String.format(Messages.Order.RECORD_DELETED_ERROR,id) +e.getMessage());
            logger.error("[OrderController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderController] Delete Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

}
