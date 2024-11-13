package com.example.springboottemplate.mapper;

import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.mapper.base.BaseMapper;
import com.example.springboottemplate.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<User, UserDto> {

    @Override
    public UserDto convertToDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createdBy(entity.getCreatedBy())
                .createdOn(entity.getCreatedDate())
                .lastModifiedBy(entity.getLastModifiedBy())
                .lastModifiedOn(entity.getLastModifiedDate())
                .isActive(entity.isActive())
                .build();
    }
}
