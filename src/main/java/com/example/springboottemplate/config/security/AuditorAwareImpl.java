package com.example.springboottemplate.config.security;

import com.example.springboottemplate.utils.IdentityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(IdentityUtils.getUser().getId());
    }
}
