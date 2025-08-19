package com.market_p.market_p.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType="Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
