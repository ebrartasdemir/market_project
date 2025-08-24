package com.market_p.market_p.controller;

import com.market_p.market_p.dto.AdressDto;
import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.entity.Adress;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.AdressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdressController {
    private static final Logger logger= LoggerFactory.getLogger(AdressController.class);
    private String message;
    @Autowired
    AdressService adressService;
    @GetMapping("/adresses")
    public ResponseEntity<ApiResponse<List<Adress>>> getAllAdresses(){
        try {
            logger.info("[AdressController] Api: GET=> /adresses ------------");
            logger.info("[AdressController] Request Body : null");
            message=Messages.Adress.RECORD_FOUND_AND_LISTED;
            List<Adress> adressList=adressService.getAllAdresses();
            ApiResponse<List<Adress>> apiResponse = new ApiResponse<>(message,adressList);
            logger.info("[AdressController] Response Body : {} ",adressList);
            logger.info("[AdressController] Listed Successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        catch (Exception e){
            message=String.format(Messages.Adress.RECORD_NOT_FOUND_AND_LISTED_ERROR);
            ApiResponse<List<Adress>> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/adresses/user")
    public ResponseEntity<ApiResponse<List<Adress>>> getAdressByUser(@AuthenticationPrincipal User user){
        int userId=user.getId();
        try {
            logger.info("[AdressController] Api: GET=> /adresses/user ------------");
            logger.info("[AdressController] Request Body : null");
            message=Messages.Adress.RECORD_FOUND_AND_LISTED;
            List<Adress> adressList=adressService.getAdressByUser(userId);
            ApiResponse<List<Adress>> apiResponse = new ApiResponse<>(message,adressList);
            logger.info("[AdressController] Response Body : {} ",adressList);
            logger.info("[AdressController] Listed Successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        catch (Exception e){
            message=String.format(Messages.Adress.RECORD_NOT_FOUND_AND_LISTED_ERROR);
            ApiResponse<List<Adress>> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/adress/{id}")
    public ResponseEntity<ApiResponse<Adress>> getAdressById(@PathVariable int id){
        try {
            logger.info("[AdressController] Api: GET=> /adress/{} ------------",id);
            logger.info("[AdressController] Request Body : null");
            message=String.format(Messages.Adress.RECORD_NOT_FOUND,id);
            Adress adress=adressService.getAdressById(id);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message,adress);
            logger.info("[AdressController] Response Body : {} ",adress);
            logger.info("[AdressController] Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        catch (Exception e){
            message=String.format(Messages.Adress.RECORD_NOT_FOUND,id);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/adress/{title}")
    public ResponseEntity<ApiResponse<Adress>> getAdressByTitle(String title){
        try {
            logger.info("[AdressController] Api: GET=> /adress/{} ------------",title);
            logger.info("[AdressController] Request Body : null");
            message=String.format(Messages.Adress.RECORD_NOT_FOUND_TITLE,title);
            Adress adress=adressService.getAdressByTitle(title);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message,adress);
            logger.info("[AdressController] Response Body : {} ",adress);
            logger.info("[AdressController] Found Successfully");
            logger.info("[AdressController] Create Failed.");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        catch (Exception e){
            message=String.format(Messages.Adress.RECORD_NOT_FOUND_TITLE,title);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/adress")
    public ResponseEntity<ApiResponse<Adress>> createAdress(@AuthenticationPrincipal User user ,@RequestBody AdressDto newAdress){
        int userId=user.getId();
        try {
            logger.info("[AdressController] Api: POST=> /adress ------------");
            logger.info("[AdressController] Request Body : {}",newAdress);
            message=String.format(Messages.Adress.RECORD_CREATED);
            adressService.addAdress(userId,newAdress);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.info("[AdressController] Response Body : null");
            logger.info("[AdressController] Created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch (Exception e){
            message=String.format(Messages.Adress.RECORD_CREATED_ERROR);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message+e.getMessage());
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Create Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/adress/{id}")
    public ResponseEntity<ApiResponse<Adress>> updateAdress(@PathVariable int id,@RequestBody AdressDto newAdress){
        try {
            logger.info("[AdressController] Api: POST=> /adress ------------");
            logger.info("[AdressController] Request Body : {}",newAdress);
            message=String.format(Messages.Adress.RECORD_UPDATED,id);
            adressService.updateAdress(id,newAdress);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.info("[AdressController] Response Body : null");
            logger.info("[AdressController] Updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        catch (Exception e){
            message=Messages.Adress.RECORD_UPDATED_ERROR;
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Update Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @DeleteMapping("/adress/{id}")
    public ResponseEntity<ApiResponse<Adress>> deleteAdress(@PathVariable int id){
        try {
            logger.info("[AdressController] Api: POST=> /adress ------------");
            logger.info("[AdressController] Request Body : null");
            message=String.format(Messages.Adress.RECORD_DELETED,id);
            adressService.deleteAdress(id);
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.info("[AdressController] Response Body : null");
            logger.info("[AdressController] Deleted Successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e){
            message=Messages.Adress.RECORD_DELETED_ERROR;
            ApiResponse<Adress> apiResponse = new ApiResponse<>(message);
            logger.error("[AdressController] Response Error : {} ",apiResponse);
            logger.info("[AdressController] Delete Failed.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

}
