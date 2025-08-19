package com.market_p.market_p.security;

import com.market_p.market_p.example.constants.Messages;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTGenerator {
    Dotenv dotenv=Dotenv.load();
    private String jwtSecret=dotenv.get("JWT_SECRET");
    @Value("${security.jwt.access-ttl-minutes}")
    private int tokenSecureMin;
    byte[] salt = "depoladigin-random-salt".getBytes(StandardCharsets.UTF_8);
    PBEKeySpec spec = new PBEKeySpec(jwtSecret.toCharArray(), salt, 150_000, 256);
    byte[] keyBytes = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");

    public JWTGenerator() throws InvalidKeySpecException, NoSuchAlgorithmException {
    }


    public String generateToken(Authentication authentication) {
        String authenticationName = authentication.getName();
        Clock clock = Clock.systemUTC();
        Instant now = Instant.now(clock);
        Instant exp  = now.plus(Duration.ofMinutes(tokenSecureMin));
        return Jwts.builder().setSubject(authenticationName)
        .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
        .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUserFromToken(String token) {
        Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token) {
    try{
        //TODO:değiştir
        //redis spring boot redis ile cahcleme
        Jwts.parserBuilder().setSigningKey(key).
                setAllowedClockSkewSeconds(60).
                build().
                parseClaimsJws(token);
        return true;
    }
    catch (Exception e) {
    throw new AuthenticationCredentialsNotFoundException(Messages.Auth.INVALID_TOKEN);
    }
    }
}
