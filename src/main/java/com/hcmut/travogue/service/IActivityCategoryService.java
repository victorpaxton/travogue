package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.TravelActivity.CategoryCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;

import java.util.List;
import java.util.UUID;

public interface IActivityCategoryService {
    public List<ActivityCategory> getTopActivityCategories();
    public ActivityCategory getActivityCategory(UUID categoryId);
    public ActivityCategory addCategory(CategoryCreateDTO categoryCreateDTO);
    public ActivityCategory addChildCategory(UUID parentId, CategoryCreateDTO categoryCreateDTO);
    public ActivityCategory updateCategory(UUID categoryId, CategoryCreateDTO categoryCreateDTO);
    public void deleteCategory(UUID categoryId);

}
