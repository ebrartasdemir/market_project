package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import com.market_p.market_p.entity.CartItem;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Cart.Items.CartItemService;
import com.market_p.market_p.service.Cart.Items.CartItemServiceImpl;
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
public class CartItemController {
    @Autowired
    private CartItemServiceImpl cartItemService;
    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    @GetMapping("/cartItems")
    public ResponseEntity<ApiResponse<List<CartItemResDto>>> getAllCartItems() {
        try{
            logger.info("[CartItemController] Api: GET=> /cartItems /------------");
            logger.info("[CartItemController] Request Body: null");
            List<CartItemResDto>cartItemList=cartItemService.getAllCartItems();
            ApiResponse<List<CartItemResDto>> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORDS_FOUND_AND_LISTED,cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            logger.info("[CartItemController] Founded Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            String message = Messages.Cart.Item.RECORD_NOT_FOUND_AND_LISTED_ERROR;
            ApiResponse<List<CartItemResDto>> apiResponse= new ApiResponse<>(message +e.getMessage());
            logger.info("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cartItems/user/{userId}")
    public ResponseEntity<ApiResponse<List<CartItemResDto>>> getAllCartItemsByUserId(@AuthenticationPrincipal User user) {
        try{
            int userId=user.getId();
            logger.info("[CartItemController] Api: GET=> /cartItems/{} /------------",userId);
            logger.info("[CartItemController] Request Body: user => {}",user);
            List<CartItemResDto>cartItemList=cartItemService.getCartItemsByUserId(userId);
            ApiResponse<List<CartItemResDto>> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORDS_FOUND_AND_LISTED,cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            logger.info("[CartItemController] Founded Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            String message=Messages.Cart.Item.RECORD_NOT_FOUND_AND_LISTED_ERROR;
            ApiResponse<List<CartItemResDto>> apiResponse= new ApiResponse<>(message +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[CartItemController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cartItems/cart/{cartId}")
    public ResponseEntity<ApiResponse<List<CartItemResDto>>> getAllCartItemsByCartId(int cartId) {
        try{
            logger.info("[CartItemController] Api: GET=> /cartItems/{} /------------",cartId);
            logger.info("[CartItemController] Request Body: null");
            List<CartItemResDto>cartItemList=cartItemService.getCartItemByCartId(cartId);
            ApiResponse<List<CartItemResDto>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",cartItemList);
            logger.info("[CartItemController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CartItemResDto>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}",toJson(apiResponse));
            logger.warn("[CartItemController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/cartItem/{id}")
    public ResponseEntity<ApiResponse<CartItemResDto>> getCartItemById(@PathVariable @Min(1) int  id) {
        try{
            logger.info("[CartItemController] Api: GET=> /cartItem/{} /------------", id);
            logger.info("[CartItemController] Request Body: null");
            CartItemResDto cartItemResDto =cartItemService.getCartItemById(id);
            String message=String.format(Messages.Cart.Item.RECORD_FOUND, id);
            if(cartItemResDto ==null) message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND, id);
            ApiResponse<CartItemResDto> apiResponse = new ApiResponse<>(message, cartItemResDto);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartItemController] Founded Successfully");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CartItemResDto> apiResponse= new ApiResponse<>(String.format(Messages.Cart.Item.RECORD_NOT_FOUND_ERROR, id)+e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}",  toJson(apiResponse));
            logger.warn("[CartItemController] Get Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/cartItem")
    public ResponseEntity<ApiResponse<String>> addCartItem(@Valid @RequestBody CartItemReqDto cartItemReqDto){
        try{
            logger.info("[CartItemController] Api: POST=> /cartItem /------------");
            logger.info("[CartItemController] Request Body: cartItemReqDto=> {}", cartItemReqDto);
            cartItemService.createCartItem(cartItemReqDto);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_CREATED);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartItemController] Created Successfully.");
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORD_CREATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}",  toJson(apiResponse));
            logger.warn("[CartItemController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/{id}")
    public ResponseEntity<ApiResponse<String>> updateCartItem(@Valid @PathVariable @Min(1) int id, @RequestBody CartItemReqDto cartItemReqDto){
        try{
            logger.info("[CartItemController] Api: PUT=> /cartItem/{} /------------", id);
            logger.info("[CartItemController] Request Body: cartItemReqDto=> {}", cartItemReqDto);
            cartItemService.updateCartItem(id, cartItemReqDto);
            String message=String.format(Messages.Cart.Item.RECORD_UPDATED,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartItemController] Updated Successfully");
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[CartItemController] Update Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/decreaseQuantity/{id}")
    public ResponseEntity<ApiResponse<String>> decreaseQuantityCartItem(@Valid @PathVariable @Min(1) int id){
        try{
            logger.info("[CartItemController] Api: PUT=> /cartItem/decreaseQuantity/{} /------------", id);
            logger.info("[CartItemController] Request Body: null");
            cartItemService.updateQuantityCartItem(id,-1);
            String message=String.format(Messages.Cart.Item.DECREASE_QUANTITY,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartItemController] Updated Successfully");
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[CartItemController] Update Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cartItem/increaseQuantity/{id}")
    public ResponseEntity<ApiResponse<String>> increaseQuantityCartItem(@Valid @PathVariable @Min(1) int id){
        try{
            logger.info("[CartItemController] Api: PUT=> /cartItem/increaseQuantity/{} /------------", id);
            logger.info("[CartItemController] Request Body: null");
            cartItemService.updateQuantityCartItem(id,+1);
            String message=String.format(Messages.Cart.Item.INCREASE_QUANTITY,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartItemController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.Item.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartItemController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[CartItemController] Update Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/cartItem/{id}")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCartItem(@PathVariable @Min(1) int id){
        try{
            logger.info("[CartItemController] Api: DELETE=> /cartItem/{} /------------", id);
            logger.info("[CartItemController] Request Body: null");
            cartItemService.deleteCartItem(id);
            logger.info("[CartItemController] Response Body: null");
            logger.info("[CartItemController] Deleted Successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[CartItemController] Error Response Body: {}", Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
            logger.warn("[CartItemController] Delete Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
        }
    }
}
