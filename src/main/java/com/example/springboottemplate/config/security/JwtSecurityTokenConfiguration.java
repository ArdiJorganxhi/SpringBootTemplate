package com.example.springboottemplate.config.security;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class JwtSecurityTokenConfiguration {

    @Value("${jwt.token.secret}")
    private String secret;

    public SecretKeySpec getSecretKeySpec() throws Exception {
        try {
            return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception ex) {
            log.error("Exception occurred : ", ex);
            throw new Exception(ex.getMessage());
        }
    }
}
