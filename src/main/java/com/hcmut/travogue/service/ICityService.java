package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.TravelActivity.City;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;

import java.util.List;
import java.util.UUID;

public interface ICityService {

    public List<City> getPopularCities();
    public List<City> getCities();
    public PageResponse<City> searchCities(int pageNumber, int pageSize, String sortField, String criteria);

    // Retrieve activities for a given city
    public PageResponse<TravelActivity> getTravelActivitiesByCity(UUID cityId, String keyword, int pageNumber, int pageSize, String sortField);

    public void dumpData();
}
