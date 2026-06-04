package com.infinite.employee_management_system.api_gateway.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey key;
    @PostConstruct
    public void init(){
        //Converting secret into key
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    //Decoding logic
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    //Extracting Email
    public String extractEmail(String token){
        return getClaims(token).getSubject();

    }
    //Extracting role
    public String extractRole(String token){
        return getClaims(token).get("role",String.class);
    }
    //Token is valid aur not
    public boolean validateToken(String token){
        return !isExpired(token);
    }

    // Expired check
    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }


}
