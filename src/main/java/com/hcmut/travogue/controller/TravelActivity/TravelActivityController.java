package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.ITravelActivityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
