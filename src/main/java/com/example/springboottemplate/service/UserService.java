package com.example.springboottemplate.service;

import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.exception.NotFoundException;
import com.example.springboottemplate.mapper.UserMapper;
import com.example.springboottemplate.model.PagingResult;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.model.request.PaginationRequest;
import com.example.springboottemplate.repository.UserRepository;
import com.example.springboottemplate.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username: %s not found", username)));
    }

    public PagingResult<UserDto> findAllUsers(PaginationRequest request) {
        final Pageable pageable = PaginationUtils
                .getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Page<User> users = userRepository.findAll(pageable);
        final List<UserDto> userDtos = users.stream().map(userMapper::convertToDto).toList();
        return new PagingResult<>(
                userDtos,
                users.getTotalPages(),
                users.getTotalElements(),
                users.getSize(),
                users.getTotalPages(),
                users.isEmpty()
        );
    }
    public UserDto findById(Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", id)));
        return userMapper.convertToDto(user);
    }

    public void deleteById(Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found", id)));
        user.setActive(false);
        userRepository.save(user);
    }
}
