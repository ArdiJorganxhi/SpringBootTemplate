package com.example.springboottemplate.config;

import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.repository.UserRepository;
import com.example.springboottemplate.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.springboottemplate.utils.MdcConstant.X_USER_ID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authenticationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authenticationHeader.substring(7);
        username = tokenService.extractUsername(jwtToken);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userRepository.findByEmail(username).orElseThrow();
            if(tokenService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getId(),
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                MDC.put(X_USER_ID, String.valueOf(userDetails.getId()));
            }
        }
        filterChain.doFilter(request, response);
    }
}
