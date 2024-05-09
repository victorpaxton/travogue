package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.TravelActivity.CategoryCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.service.IActivityCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/activity-categories")
public class ActivityCategoryController {

    @Autowired
    private IActivityCategoryService activityCategoryService;

    @GetMapping
    @Operation(summary = "Get top categories (no parent)")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getTopCategories() {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.getTopActivityCategories())
                .errors(null)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detailed category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getActivityCategory(@PathVariable("id") UUID categoryId) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.getActivityCategory(categoryId))
                .errors(null)
                .build();
    }

    @PostMapping
    @Operation(summary = "Add a category")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addCategory(@RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.addCategory(categoryCreateDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add a child category")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addChildCategory(@PathVariable("id") UUID parentId, @RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.addChildCategory(parentId, categoryCreateDTO))
                .errors(null)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> updateCategory(@PathVariable("id") UUID categoryId, @RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.updateCategory(categoryId, categoryCreateDTO))
                .errors(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> deleteCategory(@PathVariable("id") UUID categoryId) {
        activityCategoryService.deleteCategory(categoryId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Delete successfully")
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/popular-activities")
    @Operation(summary = "Get top 10 popular travel activities of a category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getPopularTravelActivitiesByCategory(@PathVariable("id") UUID categoryId) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.getPopularTravelActivitiesByCategory(categoryId))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/travel-activities")
    @Operation(summary = "Get travel activities of a category (all activities of that category and its children)")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getActivitiesByCategory(@PathVariable("id") UUID categoryId,
                                                         @RequestParam(defaultValue = "") String keyword,
                                                         @RequestParam(defaultValue = "0") int pageNumber,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         @RequestParam(defaultValue = "average_rating") String sortField) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(activityCategoryService.getTravelActivitiesByCategory(categoryId, keyword, pageNumber, pageSize, sortField))
                .errors(null)
                .build();
    }
}
