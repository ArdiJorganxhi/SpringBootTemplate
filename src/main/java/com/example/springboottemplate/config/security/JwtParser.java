package com.example.springboottemplate.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtParser {

    @Value("${jwt.token.secret}")
    private String secret;

    public Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt)
                .getBody();
    }
}
