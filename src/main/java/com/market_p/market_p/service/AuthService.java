package com.market_p.market_p.service;

import com.market_p.market_p.dto.AuthResponse;
import com.market_p.market_p.dto.LoginDto;
import com.market_p.market_p.dto.RegisterDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface AuthService {
    void register(@RequestBody RegisterDto registerDto);
    AuthResponse login(@RequestBody LoginDto loginDto);
}
