package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.TravelActivity.CategoryCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.TravelActivity.ActivityCategoryRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.repository.WishlistRepository;
import com.hcmut.travogue.service.IActivityCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityCategoryService implements IActivityCategoryService {

    @Autowired
    private ActivityCategoryRepository activityCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

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

    @Override
    public List<TravelActivity> getPopularTravelActivitiesByCategory(Principal principal, UUID categoryId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        List<UUID> allChildCategories = activityCategoryRepository.findChildCategoryIds(categoryId);

        return travelActivityRepository.findFirst10ByActivityCategory_IdOrderByAverageRatingDesc(allChildCategories)
                .stream().peek(travelActivity -> travelActivity.setLiked(wishlistRepository.existsByUser_IdAndTravelActivity_Id(user.getId(), travelActivity.getId()))).toList();
    }

    @Override
    public PageResponse<TravelActivity> getTravelActivitiesByCategory(Principal principal, UUID categoryId, String keyword, int pageNumber, int pageSize, String sortField) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        List<UUID> allChildCategories = activityCategoryRepository.findChildCategoryIds(categoryId);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());

        return new PageResponse<>(
                travelActivityRepository.findPageTravelActivitiesByCategories(allChildCategories, keyword, pageable)
                        .map(travelActivity -> {
                            travelActivity.setLiked(wishlistRepository.existsByUser_IdAndTravelActivity_Id(user.getId(), travelActivity.getId()));
                            return travelActivity;
                        })
        );
    }
}
