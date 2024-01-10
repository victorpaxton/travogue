package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.TravelActivity.City;

import java.util.List;

public interface ICityService {

    public List<City> getPopularCities();
    public List<City> getCities();
    public PageResponse<City> searchCities(int pageNumber, int pageSize, String sortField, String criteria);
}
