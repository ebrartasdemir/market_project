package com.market_p.market_p.controller;

import com.market_p.market_p.dto.*;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.AuthServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static com.market_p.market_p.utils.Jsonify.toJson;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthServiceImpl authService;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @NotNull RegisterDto  registerDto) {
        try{
            logger.info("[AuthController] Api: GET=> /auth/register /------------");
            logger.info("[AuthController] Request Body: DTO => {} ",registerDto);
            authService.register(registerDto);
            ApiResponse apiResponse=new ApiResponse(Messages.User.RECORD_CREATED_SUCCESSFULLY);
            logger.info("[AuthController] Response Body: DTO => {} ",apiResponse);
            logger.info("[AuthController] Registered Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch (Exception e){
            ApiResponse<List<CategoryResDto>> apiResponse= new ApiResponse<>(Messages.User.REGISTER_FAILED +e.getMessage());
            logger.info("[CategoryController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @NotNull LoginDto loginDto) {
        try {
            logger.info("[AuthController] Api: GET=> /auth/login /------------");
            logger.info("[AuthController] Input: DTO => {} ",loginDto);
            AuthResponse authResponse = authService.login(loginDto);
            ApiResponse<AuthResponse> apiResponse=new ApiResponse<AuthResponse>(Messages.Auth.VALID_TOKEN,authResponse);
            logger.info("[AuthController] Response Body: DTO => {} ",apiResponse);
            logger.info("[AuthController] Logged Successfully");
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        }
        catch (Exception e){
            ApiResponse<List<CategoryResDto>> apiResponse= new ApiResponse<>(Messages.User.LOGIN_FAILED +e.getMessage());
            logger.info("[AuthController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

        }
    }
}
