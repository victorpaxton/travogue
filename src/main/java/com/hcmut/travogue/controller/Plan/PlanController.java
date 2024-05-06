package com.hcmut.travogue.controller.Plan;

import com.hcmut.travogue.model.dto.Plan.AddActivityDTO;
import com.hcmut.travogue.model.dto.Plan.PlanCreateDTO;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IPlanService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @PostMapping
    @Operation(summary = "Create new plan")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> createPlan(Principal principal, @RequestBody PlanCreateDTO planCreateDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(planService.createPlan(principal, planCreateDTO))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get plans by user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getPlansByUser(@RequestParam UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(planService.getPlansByUser(userId))
                .errors(null)
                .build();
    }

    @PostMapping("/{planId}/activities")
    @Operation(summary = "Add activities to plan")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> addActivityToPlan(@PathVariable("planId") UUID planId, @RequestBody AddActivityDTO addActivityDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(planService.addActivityToPlan(planId, addActivityDTO))
                .errors(null)
                .build();
    }

    @DeleteMapping("/{planId}/activities/{activityId}")
    @Operation(summary = "removeActivityFromPlan")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> removeActivityFromPlan(@PathVariable("planId") UUID planId, @PathVariable("activityId") UUID activityId) {
        planService.removeActivityFromPlan(planId, activityId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("")
                .errors(null)
                .build();
    }

    @GetMapping("/{planId}")
    @Operation(summary = "Get a plan detail")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getPlan(@PathVariable("planId") UUID planId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(planService.getPlan(planId))
                .errors(null)
                .build();
    }
}
