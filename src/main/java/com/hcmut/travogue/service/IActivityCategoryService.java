package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.TravelActivity.CategoryCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IActivityCategoryService {
    public List<ActivityCategory> getTopActivityCategories();
    public ActivityCategory getActivityCategory(UUID categoryId);
    public ActivityCategory addCategory(CategoryCreateDTO categoryCreateDTO);
    public ActivityCategory addChildCategory(UUID parentId, CategoryCreateDTO categoryCreateDTO);
    public ActivityCategory updateCategory(UUID categoryId, CategoryCreateDTO categoryCreateDTO);
    public void deleteCategory(UUID categoryId);

    // Retrieve popular activities within a specific category
    public List<TravelActivity> getPopularTravelActivitiesByCategory(Principal principal, UUID categoryId);

    // Retrieve activities within a specific category and its children
    public PageResponse<TravelActivity> getTravelActivitiesByCategory(Principal principal, UUID categoryId, String keyword, int pageNumber, int pageSize, String sortField);

}
