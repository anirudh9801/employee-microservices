package com.infinite.employee_management_system.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        System.out.println("JWT SECRET LOADED: " + secret);
    }


    //Token Generation
    public String generateToken(String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Extract email
    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }
    //Extract Role
    public String extractRole(String token){
        return getClaims(token).get("role",String.class);
    }
    //Validate token
    public boolean validateToken(String token, String email){
        return extractEmail(token).equals(email) && !isExpired(token);
    }
    //Check Expiration
    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    //Claims Extract

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
