package com.example.springboottemplate.mapper;

import com.example.springboottemplate.model.authentication.IdentityUser;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class IdentityUserMapper {

    public IdentityUser getUser(Claims claims) {
        return IdentityUser.builder()
                .id(getStringValue(claims, "id"))
                .email(getStringValue(claims, "email"))
                .build();
    }

    private String getStringValue(Claims claims, String key) {
        final Object foundValue = claims.getOrDefault(key, StringUtils.EMPTY);
        return foundValue == null ? null : foundValue.toString();
    }
}
