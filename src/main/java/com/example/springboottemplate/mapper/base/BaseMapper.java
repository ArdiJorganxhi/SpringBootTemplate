package com.example.springboottemplate.mapper.base;

import com.example.springboottemplate.entity.base.BaseEntity;
import com.example.springboottemplate.model.dto.base.BaseDto;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {

    D convertToDto(E entity);
}
