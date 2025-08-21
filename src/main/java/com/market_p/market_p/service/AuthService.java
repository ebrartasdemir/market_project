package com.market_p.market_p.service;

import com.market_p.market_p.dto.Auth.AuthResponse;
import com.market_p.market_p.dto.Auth.LoginDto;
import com.market_p.market_p.dto.Auth.RegisterDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface AuthService {
    void register(@RequestBody RegisterDto registerDto);
    AuthResponse login(@RequestBody LoginDto loginDto);
}
