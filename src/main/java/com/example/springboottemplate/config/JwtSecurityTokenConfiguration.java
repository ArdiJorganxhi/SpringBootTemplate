package com.example.springboottemplate.config;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class JwtSecurityTokenConfiguration {

    @Value("${jwt.token.secret}")
    private String jwtTokenSecret;

    public SecretKeySpec getSecretKeySpec() throws Exception {
        try {
            return new SecretKeySpec(jwtTokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception ex) {
            log.error("Exception occured:" + ex);
            throw new Exception(ex.getMessage());
        }
    }
}
