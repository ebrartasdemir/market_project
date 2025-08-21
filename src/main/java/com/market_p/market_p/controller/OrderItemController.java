package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.entity.OrderItem;
import com.market_p.market_p.repository.OrderItemRepository;
import com.market_p.market_p.service.Order.Items.OrderItemServiceImpl;
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
public class OrderItemController {
    @Autowired
    OrderItemRepository orderItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);
    @Autowired
    private OrderItemServiceImpl orderItemService;

    @GetMapping("/order/{orderCode}/items")
    public ResponseEntity<ApiResponse<List<OrderItem>>> getAllOrderItemsByOrderCode(@PathVariable String orderCode) {
        logger.info("[OrderItemController] Api: GET=> /order/{}/items /------------", orderCode);
        try{
            List<OrderItem>orderItemList=orderItemService.getOrderItemByOrderCode(orderCode);
            ApiResponse<List<OrderItem>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",orderItemList);
            logger.info("[OrderItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<OrderItem>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{id}/items")
    public ResponseEntity<ApiResponse<List<OrderItem>>> getAllOrderItemsByOrderId(@PathVariable int orderId) {
        logger.info("[OrderItemController] Api: GET=> /order/{}/items /------------", orderId);
        try{
            List<OrderItem>orderItemList=orderItemService.getOrderItemByOrderId(orderId);
            ApiResponse<List<OrderItem>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",orderItemList);
            logger.info("[OrderItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<OrderItem>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orderItem/{id}")
    public ResponseEntity<ApiResponse<OrderItem>> getOrderItemById (@PathVariable @Min(1) int  id) {
        logger.info("[OrderItemController] Api: GET=> /orderItem/{} /------------", id);
        try{
            OrderItem orderItem=orderItemService.getOrderItemById(id);
            String message=String.format("Messages.Category.RECORD_FOUND", id);
            if(orderItem==null) message=String.format("Messages.Category.RECORD_NOT_FOUND", id);
            ApiResponse<OrderItem> apiResponse = new ApiResponse<>(message,orderItem);
            logger.info("[OrderItemController] Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<OrderItem> apiResponse= new ApiResponse<>(String.format("Messages.Category.RECORD_NOT_FOUND_ERROR", id)+e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/orderItem")
    public ResponseEntity<ApiResponse<String>> addOrderItem(@Valid @RequestBody OrderItem orderItem) {
        logger.info("[OrderItemController] Api: POST=> /orderItem /------------");
        logger.info("[OrderItemController] Request Body: {}", orderItem);
        try{
            orderItemService.createOrderItem(orderItem);
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_CREATED");
            logger.info("[OrderItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_CREATED_ERROR" +e.getMessage());
            logger.error("[OrderItemController] Error Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/orderItem/{id}")
    public ResponseEntity<ApiResponse<String>> updateOrderItem(@Valid @PathVariable @Min(1) int id, @RequestBody OrderItem orderItem) {
        logger.info("[OrderItemController] Api: PUT=> /orderItem/{} /------------", id);
        logger.info("[OrderItemController] Request Body: {}",orderItem );
        try{
            orderItemService.updateOrderItem(id,orderItem);
            String message=String.format("Messages.Category.RECORD_UPDATED",id);
            if(orderItem==null) message=String.format("Messages.Category.RECORD_NOT_FOUND",id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[OrderItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_UPDATED_ERROR" +e.getMessage());
            logger.error("[OrderItemController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }


    @DeleteMapping("/orderItem/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable @Min(1) int id){
        logger.info("[OrderItemController] Api: DELETE=> /category/{} /------------", id);
        try{
            orderItemService.deleteOrderItemById(id);
            logger.info("[OrderItemController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[OrderItemController] Error Response Body: {}", "Messages.Category.RECORD_DELETED_ERROR" +e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Messages.Category.RECORD_DELETED_ERROR" +e.getMessage());
        }
    }

}
