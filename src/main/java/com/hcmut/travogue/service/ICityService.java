package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.TravelActivity.City;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ICityService {

    public List<City> getPopularCities();
    public List<City> getCities();
    public PageResponse<City> searchCities(int pageNumber, int pageSize, String sortField, String criteria);

    // Retrieve activities for a given city
    public PageResponse<TravelActivity> getTravelActivitiesByCategoryInACity(UUID cityId, UUID mainCategoryId, String filter, String keyword, int pageNumber, int pageSize, String sortField);

    public City uploadMainImage(UUID cityId, MultipartFile image) throws IOException;

    public void dumpData();
}
