package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a user details")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_HOST')")
    public ResponseModel<Object> getUser(Principal principal, @PathVariable("id") UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(userService.getUser(principal, userId))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseModel<Object> getUsers(@RequestParam(defaultValue = "") String keyword,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "4") int pageSize,
                                          @RequestParam(defaultValue = "first_name") String sortField) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(userService.getUsers(keyword, pageNumber, pageSize, sortField))
                .errors(null)
                .build();
    }


    @GetMapping("/{id}/tickets")
    @Operation(summary = "Get tickets of a user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseModel<Object> getTicketsByUser(@PathVariable("id") UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(userService.getTicketsByUser(userId))
                .errors(null)
                .build();
    }

}
