package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.Cart.CartServiceImpl;
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
public class CartController {
    @Autowired
    CartServiceImpl cartService;
    private static final Logger logger= LoggerFactory.getLogger(CartController.class);
    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<Cart>>> getAllCarts() {
        logger.info("[CartController] Api: GET=> /carts /------------");
        try{
            List<Cart> cartList=cartService.getAllCart();
            ApiResponse<List<Cart>> apiResponse = new ApiResponse<>("Messages.Category.RECORDS_FOUND_AND_LISTED",cartList);
            logger.info("[CartContoller] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<Cart>> apiResponse= new ApiResponse<>("Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR" +e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cart/{id}")
    public ResponseEntity<ApiResponse<Cart>> getCartById(@PathVariable @Min(1) int  id) {
        logger.info("[CartContoller] Api: GET=> /cart/{} /------------", id);
        try{
            Cart cart=cartService.getCartById(id);
            String message=String.format("Messages.Category.RECORD_FOUND", id);
            if(cart==null) message=String.format("Messages.Category.RECORD_NOT_FOUND", id);
            ApiResponse<Cart> apiResponse = new ApiResponse<>(message,cart);
            logger.info("[CartContoller] Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<Cart> apiResponse= new ApiResponse<>(String.format(Messages.Category.RECORD_NOT_FOUND_ERROR, id)+e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/cart/{id}")
    public ResponseEntity<ApiResponse<Cart>> getCartByUserId(@PathVariable @Min(1) int  userId) {
        logger.info("[CartContoller] Api: GET=> /cart/{} /------------", userId);
        try{
            Cart cart=cartService.getCartByUserId(userId);
            String message=String.format("Messages.Category.RECORD_FOUND", userId);
            if(cart==null) message=String.format("Messages.Category.RECORD_NOT_FOUND", userId);
            ApiResponse<Cart> apiResponse = new ApiResponse<>(message,cart);
            logger.info("[CartContoller] Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<Cart> apiResponse= new ApiResponse<>(String.format("Messages.Category.RECORD_NOT_FOUND_ERROR", userId)+e.getMessage());
            logger.info("[CartContoller] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<String>> addCategory(@Valid @RequestBody Cart cart){
        logger.info("[CartController] Api: POST=> /cart /------------");
        logger.info("[CartController] Request Body: {}", cart);
        try{
            cartService.createCart(cart);
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_CREATED");
            logger.info("[CartController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Messages.Category.RECORD_CREATED_ERROR" +e.getMessage());
            logger.error("[CartController] Error Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/cart/{id}")
    public ResponseEntity<ApiResponse<String>> updateCart(@Valid @PathVariable @Min(1) int id, @RequestBody Cart cart){
        logger.info("[CartController] Api: PUT=> /cart/{} /------------", id);
        logger.info("[CartController] Request Body: {}",cart );
        try{
            cartService.updateCart(id,cart);
            String message=String.format("Messages.Category.RECORD_UPDATED",id);
            if(cart==null) message=String.format("Messages.Category.RECORD_NOT_FOUND",id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CartController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CartController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/cart/{id}")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCategory(@PathVariable @Min(1) int id){
        logger.info("[CartController] Api: DELETE=> /cart/{} /------------", id);
        try{
            cartService.deleteCart(id);
            logger.info("[CartController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[CartController] Error Response Body: {}", "Messages.Category.RECORD_DELETED_ERROR "+e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Messages.Category.RECORD_DELETED_ERROR" +e.getMessage());
        }
    }
}
