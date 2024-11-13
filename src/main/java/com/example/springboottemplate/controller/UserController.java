package com.example.springboottemplate.controller;

import com.example.springboottemplate.model.GenericResponse;
import com.example.springboottemplate.model.PagingResult;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.model.enums.MessageResponse;
import com.example.springboottemplate.model.request.PaginationRequest;
import com.example.springboottemplate.service.UserService;
import com.example.springboottemplate.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GenericResponse<PagingResult<UserDto>>> findAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        final PaginationRequest request = new PaginationRequest(page, size, sortField, direction);
        final PagingResult<UserDto> users = userService.findAll(request);
        return GenericResponse.success(MessageResponse.USER_FOUND, users);
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<UserDto>> getProfile() {
        final UserDto user = userService.findById(IdentityUtils.getLoggedInUser());
        return GenericResponse.success(MessageResponse.USER_FOUND, user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> getUserById(@PathVariable Long id) {
        final UserDto user = userService.findById(id);
        return GenericResponse.success(MessageResponse.USER_FOUND, user);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<GenericResponse<String>> deleteProfile() {
        userService.deleteById(IdentityUtils.getLoggedInUser());
        return GenericResponse.success(MessageResponse.USER_DELETED, "");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return GenericResponse.success(MessageResponse.USER_DELETED, "");
    }
}
