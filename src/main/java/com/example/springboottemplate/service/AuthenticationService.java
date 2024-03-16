package com.example.springboottemplate.service;

import com.example.springboottemplate.config.security.PasswordEncoder;
import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.exception.NotFoundException;
import com.example.springboottemplate.mapper.IdentityUserMapper;
import com.example.springboottemplate.mapper.UserMapper;
import com.example.springboottemplate.model.response.AuthenticationResponse;
import com.example.springboottemplate.model.authentication.UserAuthentication;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.model.request.LoginRequest;
import com.example.springboottemplate.model.request.RegisterRequest;
import com.example.springboottemplate.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final IdentityUserMapper identityUserMapper;

    @Transactional
    public void register(RegisterRequest request) {
        final boolean checkIfExists = userRepository.findByEmail(request.email()).isPresent();
        if(checkIfExists) {
            System.out.println("User already exists.");
        }
        final User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.bCryptPasswordEncoder().encode(request.password()))
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (DisabledException e) {
            throw new Exception("DisabledException");
        }

        final User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", request.email())));;
        final UserDto userDto = userMapper.convertToDto(user);
        final String token = tokenService.createToken(userDto);
        return new AuthenticationResponse(token);
    }

    public Authentication getUserAuthentication(HttpServletRequest httpServletRequest) {
        final Claims claims = tokenService.getTokenClaims(httpServletRequest);
        return new UserAuthentication(identityUserMapper.getUser(claims));
    }
}
