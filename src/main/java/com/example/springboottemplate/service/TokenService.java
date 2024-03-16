package com.example.springboottemplate.service;

import com.example.springboottemplate.config.security.JwtParser;
import com.example.springboottemplate.config.security.JwtSecurityTokenConfiguration;
import com.example.springboottemplate.exception.UnauthorizedException;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String BEARER = "Bearer ";
    private final JwtSecurityTokenConfiguration jwtSecurityTokenConfiguration;
    private final JwtParser jwtParser;
    private static final int TOKEN_EXPIRATION_HOURS_COUNT = 9;

    @SneakyThrows
    public String createToken(UserDto userDto) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("email", userDto.email());
        tokenData.put("id", userDto.id());
        final LocalDateTime now = LocalDateTime.now();

        return BEARER + Jwts.builder()
                .setClaims(tokenData)
                .setExpiration(DateUtils.convertLocalDateTimeToDate(now.plusHours(TOKEN_EXPIRATION_HOURS_COUNT)))
                .setIssuedAt(DateUtils.convertLocalDateTimeToDate(now))
                .signWith(SignatureAlgorithm.HS512, jwtSecurityTokenConfiguration.getSecretKeySpec())
                .compact();
    }

    public Claims getTokenClaims(HttpServletRequest httpServletRequest) {
        return jwtParser.getClaims(getTokenFromHeader(httpServletRequest));
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        String authenticationHeader = httpServletRequest.getHeader("Authorization");
        boolean startWithBearer = StringUtils.startsWith(authenticationHeader, "Bearer");
        String[] headerParams = StringUtils.split(authenticationHeader, StringUtils.SPACE);
        boolean headerParamSizeIsTwo = ArrayUtils.getLength(headerParams) == 2;
        boolean isAuthenticationHeaderValid = authenticationHeader != null && startWithBearer && headerParamSizeIsTwo;
        if(!isAuthenticationHeaderValid) {
            throw new UnauthorizedException("Not authorized!");
        }
        return authenticationHeader.split(StringUtils.SPACE)[1];
    }
}
