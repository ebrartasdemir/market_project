package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Order.Item.OrderItemDto;
import com.market_p.market_p.entity.OrderItem;
import com.market_p.market_p.example.constants.Messages;
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
    private String message;

    @GetMapping("/order/items")
    public ResponseEntity<ApiResponse<List<OrderItemDto>>> getAllOrderItems() {
        try{
            logger.info("[OrderItemController] Api: GET=> /order/items /------------");
            logger.info("[OrderItemController] Request Body : null");
            List<OrderItemDto> orderItemDtoList=orderItemService.getAllOrderItems();
            message= Messages.Order.Items.RECORDS_FOUND_AND_LISTED;
            ApiResponse<List<OrderItemDto>> apiResponse = new ApiResponse<>(message, orderItemDtoList);
            logger.info("[OrderItemController] Response Body : {}", apiResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        catch(Exception e){
            message=Messages.Order.Items.RECORD_NOT_FOUND_AND_LISTED_ERROR;
            ApiResponse<List<OrderItemDto>> apiResponse=new ApiResponse<>(message,null);
            logger.error("[OrderItemController] Response Body :  {}", apiResponse);
            logger.warn("[OrderItemController] Get Failed");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/order/{orderCode}/items")
    public ResponseEntity<ApiResponse<List<OrderItemDto>>> getAllOrderItemsByOrderCode(@PathVariable String orderCode) {
        try{
            logger.info("[OrderItemController] Api: GET=> /order/{}/items /------------", orderCode);
            logger.info("[OrderItemController] Request Body : {}",orderCode);
            List<OrderItemDto>orderItemList=orderItemService.getOrderItemByOrderCode(orderCode);
            message=String.format(Messages.Order.Items.RECORDS_FOUND_AND_LISTED,orderCode);
            ApiResponse<List<OrderItemDto>> apiResponse = new ApiResponse<>(message,orderItemList);
            logger.info("[OrderItemController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderItemController] Found Successfully");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            message=Messages.Order.Items.RECORD_NOT_FOUND_AND_LISTED_ERROR;
            ApiResponse<List<OrderItemDto>> apiResponse= new ApiResponse<>(message +e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderItemController] Find Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/order/{id}/items")
    public ResponseEntity<ApiResponse<List<OrderItemDto>>> getAllOrderItemsByOrderId(@PathVariable int orderId) {
        try{
            logger.info("[OrderItemController] Api: GET=> /order/{}/items /------------", orderId);
            logger.info("[OrderItemController] Request Body : null");
            List<OrderItemDto>orderItemList=orderItemService.getOrderItemByOrderId(orderId);
            message=Messages.Order.Items.RECORDS_FOUND_AND_LISTED;
            ApiResponse<List<OrderItemDto>> apiResponse = new ApiResponse<>(message,orderItemList);
            logger.info("[OrderItemController] Response Body: {}", toJson(apiResponse));
            logger.info("[OrderItemController] Found Successfully");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            message=Messages.Order.Items.RECORD_NOT_FOUND_AND_LISTED_ERROR;
            ApiResponse<List<OrderItemDto>> apiResponse= new ApiResponse<>(message +e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[OrderItemController] Find Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/orderItem/{id}")
    public ResponseEntity<ApiResponse<OrderItemDto>> getOrderItemById (@PathVariable @Min(1) int  id) {
        logger.info("[OrderItemController] Api: GET=> /orderItem/{} /------------", id);
        logger.info("[OrderItemController] Request Body : null");
        try{
            OrderItemDto orderItemDto =orderItemService.getOrderItemById(id);
            String message=String.format(Messages.Order.Items.RECORD_FOUND, id);
            if(orderItemDto ==null) message=String.format(Messages.Order.Items.RECORD_NOT_FOUND, id);
            ApiResponse<OrderItemDto> apiResponse = new ApiResponse<>(message, orderItemDto);
            logger.info("[OrderItemController] Response Body: {}",  toJson(apiResponse));
            logger.info("[OrderItemController] Found Successfully");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<OrderItemDto> apiResponse= new ApiResponse<>(Messages.Order.Items.RECORD_NOT_FOUND_AND_LISTED_ERROR+e.getMessage());
            logger.info("[OrderItemController] Error Response Body: {}",  toJson(apiResponse));
            logger.warn("[OrderItemController] Find Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/orderItem")
    public ResponseEntity<ApiResponse<String>> addOrderItem(@Valid @RequestBody OrderItem orderItem) {
        try{
            logger.info("[OrderItemController] Api: POST=> /orderItem /------------");
            logger.info("[OrderItemController] Request Body: {}", orderItem);
            orderItemService.createOrderItem(orderItem);
            message=Messages.Order.Items.RECORD_CREATED;
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message));
            logger.info("[OrderItemController] Response Body: null");
            logger.info("[OrderItemController] Created Successfully");
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Order.Items.RECORD_CREATED_ERROR +e.getMessage());
            logger.error("[OrderItemController] Error Response Body: {}",  toJson(apiResponse));
            logger.info("[OrderItemController] Update Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/orderItem/{id}")
    public ResponseEntity<ApiResponse<String>> updateOrderItem(@Valid @PathVariable @Min(1) int id, @RequestBody OrderItem orderItem) {
        try{
            logger.info("[OrderItemController] Api: PUT=> /orderItem/{} /------------", id);
            logger.info("[OrderItemController] Request Body: {}",orderItem );
            orderItemService.updateOrderItem(id,orderItem);
            message=String.format(Messages.Order.Items.RECORD_UPDATED,id);
            if(orderItem==null) {message=String.format(Messages.EMPTY_BODY);
            logger.error("[OrderItemController] Error Response Body: {}",  toJson(orderItem));
            logger.warn("[OrderItemController] Update Failed");
            }
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[OrderItemController] Response Body: null");
            logger.info("[OrderItemController] Updated Successfully");
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Order.Items.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[OrderItemController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[OrderItemController] Update Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }


    @DeleteMapping("/orderItem/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable @Min(1) int id){
        logger.info("[OrderItemController] Api: DELETE=> /category/{} /------------", id);
        try{
            logger.info("[OrderItemController] Request Body: null");
            orderItemService.deleteOrderItemById(id);
            logger.info("[OrderItemController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            logger.info("[OrderItemController] Deleted Successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            message=String.format(Messages.Order.Items.RECORD_DELETED_ERROR+e.getMessage());
            logger.error("[OrderItemController] Error Response Body: {}", message +e.getMessage());
            logger.warn("[OrderItemController] Deleted Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message +e.getMessage());
        }
    }

}
