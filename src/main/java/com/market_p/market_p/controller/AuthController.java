package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.AuthResponse;
import com.market_p.market_p.dto.LoginDto;
import com.market_p.market_p.dto.RegisterDto;
import com.market_p.market_p.entity.Role;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.repository.RoleRepository;
import com.market_p.market_p.repository.UserRepository;
import com.market_p.market_p.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto  registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(String.format(Messages.User.ALREADY_REGISTERED_EMAIL, registerDto.getEmail()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        String password = passwordEncoder.encode(registerDto.getPassword());
        Role role=roleRepository.findByTitle("USER").get();
        User user = new User(registerDto.getFirstName(), registerDto.getSurname(),password, registerDto.getEmail(), registerDto.getPhoneNum(),role);
        userRepository.save(user);
        ApiResponse apiResponse=new ApiResponse(Messages.User.RECORD_CREATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        if(!userRepository.existsByEmail(loginDto.getEmail())) {
            ApiResponse apiResponse=new ApiResponse<>(String.format(Messages.User.RECORD_NOT_FOUND_BY_EMAIL,loginDto.getEmail()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtGenerator.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(token);
        ApiResponse<AuthResponse> apiResponse=new ApiResponse<AuthResponse>(Messages.Auth.VALID_TOKEN,authResponse);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
