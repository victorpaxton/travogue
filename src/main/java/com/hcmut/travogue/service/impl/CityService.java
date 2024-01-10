package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.TravelActivity.City;
import com.hcmut.travogue.repository.TravelActivity.CityRepository;
import com.hcmut.travogue.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getPopularCities() {
        return cityRepository.findFirst10ByOrderByTravelPoint();
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public PageResponse<City> searchCities(int pageNumber, int pageSize, String sortField, String criteria) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        return new PageResponse<>(cityRepository.findPageCities(criteria, pageable));
    }
}
