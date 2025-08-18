package com.market_p.market_p.security;

import com.market_p.market_p.example.constants.Messages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.access-ttl-minutes}")
    private int tokenSecureMin;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + tokenSecureMin * 60 * 1000);
        String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }
    public String getUserFromToken(String token) {
        Claims claims=Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token) {
    try{
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        return true;
    }
    catch (Exception e) {
    throw new AuthenticationCredentialsNotFoundException(Messages.Auth.INVALID_TOKEN);
    }
    }
}
