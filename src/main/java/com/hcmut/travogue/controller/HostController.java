package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IHostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private IHostService hostService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a host details")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_HOST')")
    public ResponseModel<Object> getHost(@PathVariable("id") UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(hostService.getHost(userId))
                .errors(null)
                .build();
    }
}
