package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCommentDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCreateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityDateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityTimeFrameDTO;
import com.hcmut.travogue.service.ITravelActivityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/travel-activities")
public class TravelActivityController {

    @Autowired
    private ITravelActivityService travelActivityService;

    @GetMapping("/popular")
    @Operation(summary = "Get top 10 popular travel activities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getPopularTravelActivities() {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getPopularTravelActivities())
                .errors(null)
                .build();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a detailed travel activities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getTravelActivity(@PathVariable("id") UUID id) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getTravelActivity(id))
                .errors(null)
                .build();

    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Add comment on an activity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> comment(Principal principal,
                                                   @PathVariable("id") UUID activityId,
                                                   @RequestBody @Valid ActivityCommentDTO activityCommentDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.comment(principal, activityId, activityCommentDTO))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get comments of an activity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getCommentsByActivity(Principal principal, @PathVariable("id") UUID activityId) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getCommentsByActivity(principal, activityId))
                .errors(null)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a travel activity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> createActivity(@RequestParam UUID categoryId, @RequestBody ActivityCreateDTO activityCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.createActivity(categoryId, activityCreateDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/experiences")
    @Operation(summary = "Host creates a travel experience")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> createExperience(Principal principal, @RequestBody ActivityCreateDTO activityCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.createExperience(principal, activityCreateDTO))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get travel activities of a host")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getActivitiesByHost(@RequestParam UUID hostId,
                                                     @RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "4") int pageSize,
                                                     @RequestParam(defaultValue = "created_at") String sortField) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getActivitiesByHost(hostId, pageNumber, pageSize, sortField))
                .errors(null)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update information of a travel activity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> updateActivity(@PathVariable("id") UUID activityId,
                                                @RequestBody ActivityCreateDTO activityCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.updateActivity(activityId, activityCreateDTO))
                .errors(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a travel activities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> deleteActivity(@PathVariable("id") UUID id) {
        travelActivityService.deleteActivity(id);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Deleted successfully")
                .errors(null)
                .build();

    }

    @PostMapping("/experiences/{id}")
    @Operation(summary = "Add activity date")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addActivityDate(@PathVariable("id") UUID activityId, @RequestBody ActivityDateDTO activityDateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.addActivityDate(activityId, activityDateDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/experiences/activity-dates/{id}")
    @Operation(summary = "Add time frame for an activity date")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addActivityTimeFrame(@PathVariable("id") UUID activityDateId, @RequestBody ActivityTimeFrameDTO activityTimeFrameDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.addActivityTimeFrame(activityDateId, activityTimeFrameDTO))
                .errors(null)
                .build();
    }
}
