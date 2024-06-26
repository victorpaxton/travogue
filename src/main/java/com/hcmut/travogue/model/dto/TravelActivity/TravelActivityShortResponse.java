package com.hcmut.travogue.model.dto.TravelActivity;

import lombok.Data;

import java.util.UUID;

@Data
public class TravelActivityShortResponse {
    private UUID id;
    private String activityName;
    private String mainImage;
    private String categoryName;
    private String cityName;
    private double rating;
    private Integer generalPrice;
    private int numberOfRating;
}
