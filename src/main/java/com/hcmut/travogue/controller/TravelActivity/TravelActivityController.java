package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.TravelActivity.*;
import com.hcmut.travogue.service.ITravelActivityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseModel<Object> getPopularTravelActivities(Principal principal) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getPopularTravelActivities(principal))
                .errors(null)
                .build();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a detailed travel activities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getTravelActivity(Principal principal, @PathVariable("id") UUID id) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.getTravelActivity(principal, id))
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
    public ResponseModel<Object> createActivity(@RequestParam UUID categoryId, @RequestParam UUID cityId, @RequestBody ActivityCreateDTO activityCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.createActivity(categoryId, cityId, activityCreateDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/experiences")
    @Operation(summary = "Host creates a travel experience")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> createExperience(Principal principal,
                                                  @RequestParam UUID categoryId,
                                                  @RequestParam UUID cityId,
                                                  @RequestBody ExperienceCreateDTO experienceCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.createExperience(principal, categoryId, cityId, experienceCreateDTO))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get travel activities of a host")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getActivitiesByHost(@RequestParam UUID hostId,
                                                     @RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "4") int pageSize,
                                                     @RequestParam(defaultValue = "updatedAt") String sortField) {
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

    @PostMapping(value = "/{id}/main-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload main image for an activity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> uploadMainImage(@PathVariable("id") UUID activityId, @RequestPart MultipartFile image) throws IOException {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.uploadMainImage(activityId, image))
                .errors(null)
                .build();
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload other images for an activity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> uploadImage(@PathVariable("id") UUID activityId, @RequestPart MultipartFile image) throws IOException {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.uploadImage(activityId, image))
                .errors(null)
                .build();
    }

//    @PostMapping(value = "/{id}/videos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Upload other images for an activity")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseModel<Object> uploadVideo(@PathVariable("id") UUID activityId, @RequestPart MultipartFile video) throws IOException {
//
//        return ResponseModel.builder()
//                .isSuccess(true)
//                .data(travelActivityService.uploadVideo(activityId, video))
//                .errors(null)
//                .build();
//    }

    @GetMapping("/search")
    @Operation(summary = "Search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> searchActivities(@RequestParam String criteria,
                                                     @RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "4") int pageSize,
                                                     @RequestParam(defaultValue = "updated_at") String sortField,
                                                  @RequestParam(required = false) UUID cityId) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(travelActivityService.searchActivities(pageNumber, pageSize, sortField, criteria, cityId))
                .errors(null)
                .build();
    }
}
