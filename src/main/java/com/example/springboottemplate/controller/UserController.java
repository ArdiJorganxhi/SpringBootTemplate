package com.example.springboottemplate.controller;

import com.example.springboottemplate.model.PagingResult;
import com.example.springboottemplate.model.request.PaginationRequest;
import com.example.springboottemplate.model.response.GenericResponse;
import com.example.springboottemplate.model.dto.UserDto;
import com.example.springboottemplate.service.UserService;
import com.example.springboottemplate.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GenericResponse<PagingResult<UserDto>>> getAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction direction
            ) {
        final PaginationRequest request = new PaginationRequest(page, size, sortField, direction);
        final PagingResult<UserDto> userDtoPagingResult = userService.findAllUsers(request);
        return ResponseEntity.ok(GenericResponse.success(userDtoPagingResult));
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<UserDto>> getProfile() {
        final UserDto user = userService.findById(Integer.valueOf(IdentityUtils.getUser().getId()));
        return ResponseEntity.ok(GenericResponse.success(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> getUserById(@PathVariable Integer id) {
        final UserDto user = userService.findById(id);
        return ResponseEntity.ok(GenericResponse.success(user));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<GenericResponse<String>> deleteProfile() {
        userService.deleteById(Integer.valueOf(IdentityUtils.getUser().getId()));
        return ResponseEntity.ok(GenericResponse.empty(String.format("User with id: %s is deleted successfully", IdentityUtils.getUser().getId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<String>> deleteUserByid(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok(GenericResponse.empty(String.format("User with id: %s is deleted successfully", id)));
    }
}
