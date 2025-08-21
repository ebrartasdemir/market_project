package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.entity.CartItem;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Cart.CartService;
import com.market_p.market_p.service.Cart.Items.CartItemService;
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
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    @GetMapping("/cartItems")
    public ResponseEntity<ApiResponse<List<CartItem>>> getAllCartItems() {
        logger.info("[CartItemController] Api: GET=> /cartItems /------------");
        try{
            List<CartItem>cartItemList=cartItemService.getAllCartItems();
            ApiResponse<List<CartItem>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CartItem>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cartItems/{userId}")
    public ResponseEntity<ApiResponse<List<CartItem>>> getAllCartItemsByUserId(int userId) {
        logger.info("[CartItemController] Api: GET=> /cartItem/{} /------------",userId);
        try{
            List<CartItem>cartItemList=cartItemService.getCartItemsByUserId(userId);
            ApiResponse<List<CartItem>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CartItem>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cartItems/{cartId}")
    public ResponseEntity<ApiResponse<List<CartItem>>> getAllCartItemsByCartId(int cartId) {
        logger.info("[CartItemController] Api: GET=> /cartItem/{} /------------",cartId);
        try{
            List<CartItem>cartItemList=cartItemService.getCartItemByCartId(cartId);
            ApiResponse<List<CartItem>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CartItem>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/cartItem/{id}")
    public ResponseEntity<ApiResponse<CartItem>> getCategoryById(@PathVariable @Min(1) int  id) {
        logger.info("[CartItemController] Api: GET=> /cartItem/{} /------------", id);
        try{
            CartItem cartItem=cartItemService.getCartItemById(id);
            String message=String.format("Messages.Category.RECORD_FOUND", id);
            if(cartItem==null) message=String.format("Messages.Category.RECORD_NOT_FOUND", id);
            ApiResponse<CartItem> apiResponse = new ApiResponse<>(message,cartItem);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CartItem> apiResponse= new ApiResponse<>("String.format(Messages.Category.RECORD_NOT_FOUND_ERROR, id)"+e.getMessage());
            logger.info("[CartItemController] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/cartItem")
    public ResponseEntity<ApiResponse<String>> addCategory(@Valid @RequestBody CartItemReqDto cartItemReqDto){
        logger.info("[CartItemController] Api: POST=> /cartItem /------------");
        logger.info("[CartItemController] Request Body: {}", cartItemReqDto);
        try{
            cartItemService.createCartItem(cartItemReqDto);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_CREATED);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_CREATED_ERROR" +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/{id}")
    public ResponseEntity<ApiResponse<String>> updateCartItem(@Valid @PathVariable @Min(1) int id, @RequestBody CartItemReqDto cartItemReqDto){
        logger.info("[CartItemController] Api: PUT=> /cartItem/{} /------------", id);
        logger.info("[CartItemController] Request Body: {}", cartItemReqDto);
        try{
            cartItemService.updateCartItem(id, cartItemReqDto);
            String message=String.format(Messages.Category.RECORD_UPDATED,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/decreaseQuantity/{id}")
    public ResponseEntity<ApiResponse<String>> decreaseQuantityCartItem(@Valid @PathVariable @Min(1) int id){
        logger.info("[CartItemController] Api: PUT=> /cartItem/decreaseQuantity/{} /------------", id);
        try{
            cartItemService.updateQuantityCartItem(id,-1);
            String message=String.format(Messages.Category.RECORD_UPDATED,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/decreaseQuantity/{id}")
    public ResponseEntity<ApiResponse<String>> increaseQuantityCartItem(@Valid @PathVariable @Min(1) int id){
        logger.info("[CartItemController] Api: PUT=> /cartItem/increaseQuantity/{} /------------", id);
        try{
            cartItemService.updateQuantityCartItem(id,+1);
            String message=String.format(Messages.Category.RECORD_UPDATED,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/cartItem/{id}")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCartItem(@PathVariable @Min(1) int id){
        logger.info("[CartItemController] Api: DELETE=> /cartItem/{} /------------", id);
        try{
            cartItemService.deleteCartItem(id);
            logger.info("[CartItemController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[CartItemController] Error Response Body: {}", Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
        }
    }
}
