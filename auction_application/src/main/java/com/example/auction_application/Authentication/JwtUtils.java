package com.example.auction_application.Authentication;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private final JwtConfigProperties jwtConfigProperties;

    public JwtUtils(JwtConfigProperties jwtConfigProperties){
        this.jwtConfigProperties = jwtConfigProperties;
    }

    public String generateToken(String userEmail, Long userId){
        return Jwts.builder()
        .setSubject(userEmail)
        .claim("userId", userId)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtConfigProperties.getExpirationLimit()))
        .signWith(Keys.hmacShaKeyFor(jwtConfigProperties.getSecret().getBytes()))
        .compact();
    }

    public Long extractUserId(String token){
        return Long.parseLong(extractClaims(token).get("userId").toString());
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtConfigProperties.getSecret().getBytes())).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, String userEmail) {
        String tokenUserEmail = extractClaims(token).getSubject();
        return (userEmail.equals(tokenUserEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
}
