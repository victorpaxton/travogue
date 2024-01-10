package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.ICityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private ICityService cityService;

    @GetMapping("/popular")
    @Operation(summary = "Get top 10 popular cities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getPopularCities() {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(cityService.getPopularCities())
                .errors(null)
                .build();

    }

    @GetMapping
    @Operation(summary = "Get all cities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getCities() {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(cityService.getCities())
                .errors(null)
                .build();

    }

    @GetMapping("/search")
    @Operation(summary = "Search cities with pagination")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> searchCities(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "4") int pageSize,
                                            @RequestParam(defaultValue = "id") String sortField) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(cityService.searchCities(pageNumber, pageSize, sortField, keyword))
                .errors(null)
                .build();

    }

    @PostMapping("/dumpdata")
    @Operation(summary = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> dumpData() {
        cityService.dumpData();

        return ResponseModel.builder()
                .isSuccess(true)
                .data("Completed")
                .errors(null)
                .build();

    }
}
