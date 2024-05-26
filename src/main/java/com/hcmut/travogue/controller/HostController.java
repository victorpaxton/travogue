package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IHostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private IHostService hostService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a host details")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_HOST','ROLE_USER')")
    public ResponseModel<Object> getHost(@PathVariable("id") UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(hostService.getHost(userId))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/active-dates")
    @Operation(summary = "Get active dates")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_HOST','ROLE_USER')")
    public ResponseModel<Object> getActiveDate(@PathVariable("id") UUID hostId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(hostService.getActiveDate(hostId))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/schedule-in-a-date")
    @Operation(summary = "Get schedule in a date")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_HOST','ROLE_USER')")
    public ResponseModel<Object> getScheduleInADay(@PathVariable("id") UUID hostId, @Param("date") Date date) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(hostService.getScheduleInADay(hostId, date))
                .errors(null)
                .build();
    }
}
