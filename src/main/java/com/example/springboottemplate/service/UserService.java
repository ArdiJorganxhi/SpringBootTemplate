package com.example.springboottemplate.service;

import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.mapper.UserMapper;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.repository.UserRepository;
import com.example.springboottemplate.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserDto, UserRepository, UserMapper> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
