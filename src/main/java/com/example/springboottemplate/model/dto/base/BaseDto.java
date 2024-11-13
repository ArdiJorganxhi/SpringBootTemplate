package com.example.springboottemplate.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

    private Long id;
    private Date createdOn;
    private String createdBy;
    private Date lastModifiedOn;
    private String lastModifiedBy;
    private boolean isActive;
}
