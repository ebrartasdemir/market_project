package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.Cart.CartAdminDto;
import com.market_p.market_p.dto.Cart.CartDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Cart.CartServiceImpl;
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
public class CartController {
    @Autowired
    CartServiceImpl cartService;
    private static final Logger logger= LoggerFactory.getLogger(CartController.class);
    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<CartAdminDto>>> getAllCarts() {
        logger.info("[CartController] Api: GET=> /carts /------------");
        try{
            List<CartAdminDto> cartList=cartService.getAllCart();
            ApiResponse<List<CartAdminDto>> apiResponse = new ApiResponse<>(Messages.Cart.RECORDS_FOUND_AND_LISTED,cartList);
            logger.info("[CartContoller] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CartAdminDto>> apiResponse= new ApiResponse<>(Messages.Cart.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",toJson(apiResponse));
            logger.info("[CartContoller] Listed Successfully.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cart/{id}")
    public ResponseEntity<ApiResponse<CartAdminDto>> getCartById(@PathVariable @Min(1) int  id) {
        logger.info("[CartContoller] Api: GET=> /cart/{} /------------", id);
        try{
            CartAdminDto cart=cartService.getCartById(id);
            String message=String.format("Messages.Category.RECORD_FOUND", id);
            if(cart==null) message=String.format("Messages.Category.RECORD_NOT_FOUND", id);
            ApiResponse<CartAdminDto> apiResponse = new ApiResponse<>(message,cart);
            logger.info("[CartContoller] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartContoller] Listed Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CartAdminDto> apiResponse= new ApiResponse<>(String.format(Messages.Category.RECORD_NOT_FOUND_ERROR, id)+e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cart/user")
    public ResponseEntity<ApiResponse<CartDto>> getCartByUserId(@AuthenticationPrincipal User user) {
        int userId=user.getId();
        try{
            logger.info("[CartContoller] Api: GET=> /cart/user /------------");
            CartDto cart=cartService.getCartByUserId(userId);
            String message=String.format(Messages.Cart.RECORD_FOUND_BY_USER_ID, userId);
            if(cart==null) message=Messages.EMPTY_BODY;
            ApiResponse<CartDto> apiResponse = new ApiResponse<>(message,cart);
            logger.info("[CartContoller] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartContoller] Found Successfully.");
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CartDto> apiResponse= new ApiResponse<>(String.format(Messages.Cart.RECORD_NOT_FOUND_BY_USER_ID, userId)+e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<String>> addCategory(@AuthenticationPrincipal User user){
        logger.info("[CartController] Api: POST=> /cart /------------");
        logger.info("[CartController] Request Body: null");
        int userId=user.getId();
        try{
            cartService.createCart(userId);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.RECORD_CREATED);
            logger.info("[CartController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartController] Created Successfully.");
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Cart.RECORD_CREATED_ERROR +e.getMessage());
            logger.error("[CartController] Error Response Body: {}",  toJson(apiResponse));
            logger.warn("[CartController] Create Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cart")
    public ResponseEntity<ApiResponse<String>> updateCart(@AuthenticationPrincipal User user, @RequestBody Cart cart){
        int userId= user.getId();
        logger.info("[CartController] Api: PUT=> /cart /------------");
        logger.info("[CartController] Request Body: {}",cart );
        try{
            cartService.updateCart(userId,cart);
            String message=String.format(Messages.Cart.RECORD_FOUND_BY_USER_ID,userId);
            if(cart==null) message=String.format(Messages.EMPTY_BODY);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartController] Response Body: {}",  toJson(apiResponse));
            logger.info("[CartController] Updated Successfully.");
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartController] Error Response Body: {}", toJson(apiResponse));
            logger.warn("[CartController] Update Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/cart")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCategory(@AuthenticationPrincipal User user){
        int userId=user.getId();
        logger.info("[CartController] Api: DELETE=> /cart/{} /------------", userId);
        try{
            cartService.deleteCart(userId);
            logger.info("[CartController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            logger.info("[CartController] Deleted Successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[CartController] Error Response Body: {}", Messages.Cart.RECORD_DELETED_ERROR+e.getMessage());
            logger.info("[CartController] Delete Failed");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Messages.Category.RECORD_DELETED_ERROR" +e.getMessage());
        }
    }
}
