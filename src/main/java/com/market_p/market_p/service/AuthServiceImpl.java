package com.market_p.market_p.service;

import com.market_p.market_p.dto.AuthResponse;
import com.market_p.market_p.dto.LoginDto;
import com.market_p.market_p.dto.RegisterDto;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.UserMapper;
import com.market_p.market_p.repository.UserRepository;
import com.market_p.market_p.security.JWTGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger= LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;
    private String message;

    @Override
    public void register(RegisterDto registerDto) {
        logger.info("[AuthService] Service Method: register - (registerDto) ) -  ------------");
        logger.info("[AuthService] Input: DTO => {} ",registerDto);
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            message=String.format(Messages.User.ALREADY_REGISTERED_EMAIL,registerDto.getEmail());
            logger.error("[AuthService] Error: {}",message);
            logger.warn("[AuthService] Creation Failed");
            throw  new RuntimeException(message);
        }
        String password = passwordEncoder.encode(registerDto.getPassword());
        User user= userMapper.registerDtoToUser(password,registerDto);
        userRepository.save(user);
        logger.info("[AuthService] Output : null");
        logger.info("[AuthService] Created Successfully");
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
        logger.info("[AuthService] Service Method: login - (loginDto) ) -  ------------");
        logger.info("[AuthService] Input: DTO => {} ",loginDto);
        if(!userRepository.existsByEmail(loginDto.getEmail())) {
            logger.error("[AuthService] Error: {}",message);
            logger.warn("[AuthService] Login Failed");
           throw new RuntimeException(String.format(Messages.User.RECORD_NOT_FOUND_BY_EMAIL,loginDto.getEmail()));
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtGenerator.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(token);
        logger.info("[AuthService] Created Successfully");
        logger.info("[AuthService] Output : DTO => {}", authResponse);
        return authResponse;
    }

}

