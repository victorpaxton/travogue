package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.TravelActivity.CategoryCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.repository.TravelActivity.ActivityCategoryRepository;
import com.hcmut.travogue.service.IActivityCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityCategoryService implements IActivityCategoryService {

    @Autowired
    private ActivityCategoryRepository activityCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ActivityCategory> getTopActivityCategories() {
        return activityCategoryRepository.findByParentCategoryIsNull();
    }

    @Override
    public ActivityCategory getActivityCategory(UUID categoryId) {
        return activityCategoryRepository.findById(categoryId).orElseThrow();
    }

    @Override
    public ActivityCategory addCategory(CategoryCreateDTO categoryCreateDTO) {
        return activityCategoryRepository.save(modelMapper.map(categoryCreateDTO, ActivityCategory.class));
    }

    @Override
    public ActivityCategory addChildCategory(UUID parentId, CategoryCreateDTO categoryCreateDTO) {
        ActivityCategory newCategory = modelMapper.map(categoryCreateDTO, ActivityCategory.class);
        newCategory.setParentCategory(activityCategoryRepository.findById(parentId).orElseThrow());
        return activityCategoryRepository.save(newCategory);
    }

    @Override
    public ActivityCategory updateCategory(UUID categoryId, CategoryCreateDTO categoryCreateDTO) {
        ActivityCategory category = activityCategoryRepository.findById(categoryId).orElseThrow();
        category.setCategoryName(categoryCreateDTO.getCategoryName());
        category.setDescription(categoryCreateDTO.getDescription());
        category.setSvg(category.getSvg());

        return activityCategoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        activityCategoryRepository.deleteById(categoryId);
    }
}
